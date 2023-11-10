package com.mergeco.oiljang.auth.config;

import com.mergeco.oiljang.auth.filter.CustomAuthenticationFilter;
import com.mergeco.oiljang.auth.handler.*;
/*import lombok.RequiredArgsConstructor;*/
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
/*import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;*/
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.Filter;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@EnableWebSecurity
public class WebSecurityConfig {

    private final TokenProvider tokenProvider;

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final CustomAuthenticationFilter customAuthenticationFilter;
    private final CustomAuthSuccessHandler customAuthSuccessHandler;
    private final CustomAuthFailureHandler customAuthFailureHandler;


    public WebSecurityConfig(TokenProvider tokenProvider, JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, JwtAccessDeniedHandler jwtAccessDeniedHandler, CustomAuthenticationFilter customAuthenticationFilter, CustomAuthSuccessHandler customAuthSuccessHandler, CustomAuthFailureHandler customAuthFailureHandler) {
        this.tokenProvider = tokenProvider;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
        this.customAuthenticationFilter = customAuthenticationFilter;
        this.customAuthSuccessHandler = customAuthSuccessHandler;
        this.customAuthFailureHandler = customAuthFailureHandler;
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/css/**", "/js/**", "/images/**",
                "/lib/**");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                .and()
                .authorizeRequests()
                .antMatchers("/").authenticated()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers("/swagger-ui/index.html","/swagger-ui.html", "/swagger-ui/**", "/webjars/**", "/v2/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/security").permitAll()
                .antMatchers("/users/**","/reports/**","/messages/**","/products/**","/categories/**","/wishLists/**").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/**").hasAnyRole("USER","ADMIN","ALL")
                //.anyRequest().permitAll()

                .and()
               /* .oauth2Login()
                .successHandler(oAuth2LoginSuccessHandler())
                .failureHandler(oAuth2LoginFailureHandler())
                .userInfoEndpoint().userService(customOAuth2UserService)
                .and()*/
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .cors()

                .and()
                .apply(new JwtSecurityConfig(tokenProvider))

                .and()
                .formLogin().disable()
               // .addFilterBefore(customAuthFilter(), UsernamePasswordAuthenticationFilter.class)
                .httpBasic().disable();

        return http.build();
    }

    @Bean
    public Filter customAuthFilter() {
        customAuthenticationFilter.setFilterProcessesUrl("/login");
        customAuthenticationFilter.setAuthenticationSuccessHandler(customAuthSuccessHandler);
        customAuthenticationFilter.setAuthenticationFailureHandler(customAuthFailureHandler);
        customAuthenticationFilter.afterPropertiesSet();
        return customAuthenticationFilter;
    }



}
