package com.mergeco.oiljang.auth.config;

import com.mergeco.oiljang.auth.filter.CustomAuthenticationFilter;
import com.mergeco.oiljang.auth.handler.*;
/*import lombok.RequiredArgsConstructor;*/
import com.mergeco.oiljang.auth.model.DetailsUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
/*import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;*/
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@EnableWebSecurity
@Slf4j
public class WebSecurityConfig {

    private final TokenProvider tokenProvider;
    
    private final BCryptPasswordEncoder passwordEncoder;

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private final CustomAuthenticationProvider customAuthenticationProvider;

    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final CustomAuthenticationFilter customAuthenticationFilter;
    private final CustomAuthSuccessHandler customAuthSuccessHandler;
    private final CustomAuthFailureHandler customAuthFailureHandler;

    private final UserDetailsService userDetailsService;


    public WebSecurityConfig(TokenProvider tokenProvider, BCryptPasswordEncoder passwordEncoder, JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, JwtAccessDeniedHandler jwtAccessDeniedHandler, CustomAuthenticationFilter customAuthenticationFilter, CustomAuthSuccessHandler customAuthSuccessHandler, CustomAuthFailureHandler customAuthFailureHandler, UserDetailsService userDetailsService, CustomAuthenticationProvider customAuthenticationProvider) {
        this.tokenProvider = tokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
        this.customAuthenticationFilter = customAuthenticationFilter;
        this.customAuthSuccessHandler = customAuthSuccessHandler;
        this.customAuthFailureHandler = customAuthFailureHandler;
        this.userDetailsService = userDetailsService;
        this.customAuthenticationProvider = customAuthenticationProvider;
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/css/**", "/js/**", "/images/**",
                "/lib/**","/swagger-ui/index.html","/swagger-ui.html", "/swagger-ui/**",
                "/webjars/**", "/v2/api-docs", "/configuration/ui", "/swagger-resources/**",
                "/configuration/security");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        log.info("Configuring WebSecurity...");

        http.csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                .and()
                .authorizeRequests()
                .antMatchers("/").authenticated()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers("/inquiries/**" ,"/reports/**","/messages/**","/products/**","/categories/**","/wishLists/**").permitAll()
                .antMatchers("/login","/auth/**").permitAll()
                .antMatchers("/join").permitAll()
                .antMatchers("/users/**").hasAnyRole("USER","ADMIN","ALL")
                .antMatchers("/**").hasAnyRole("USER","ADMIN","ALL")
                .anyRequest().authenticated()

                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .cors()

                .and()
                .apply(new JwtSecurityConfig(tokenProvider,userDetailsService))

                .and()
                .formLogin().disable()
                .apply(customAuthConfigurer())
                .and()
                .apply(joinAuthConfigurer())
                .and()
                .httpBasic().disable();

        return http.build();
    }

    private AuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        provider.setPreAuthenticationChecks((userDetails) -> {
            // 사용자가 탈퇴 상태인 경우 로그인을 막음
            if ("Y".equals(((DetailsUser) userDetails).getUser().getWithdrawStatus())) {
                throw new DisabledException("탈퇴한 사용자입니다.");
            }
        });
        return provider;
    }



    @Bean
    public SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> customAuthConfigurer() {
        return new SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>() {
            @Override
            public void configure(HttpSecurity http) throws Exception {
                CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(customAuthenticationProvider);
                customAuthenticationFilter.setFilterProcessesUrl("/login");
                customAuthenticationFilter.setAuthenticationSuccessHandler(customAuthSuccessHandler);
                customAuthenticationFilter.setAuthenticationFailureHandler(customAuthFailureHandler);
                customAuthenticationFilter.afterPropertiesSet();

                http.addFilterBefore(customAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
            }
        };
    }

    @Bean
    public SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> joinAuthConfigurer() {
        return new SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>() {
            @Override
            public void configure(HttpSecurity http) throws Exception {
                CustomAuthenticationFilter joinAuthFilter = new CustomAuthenticationFilter(customAuthenticationProvider);
                joinAuthFilter.setFilterProcessesUrl("/join");
                //joinAuthFilter.setAuthenticationSuccessHandler(customAuthSuccessHandler);
                //joinAuthFilter.setAuthenticationFailureHandler(customAuthFailureHandler);
                joinAuthFilter.afterPropertiesSet();
            }
        };
    }

}
