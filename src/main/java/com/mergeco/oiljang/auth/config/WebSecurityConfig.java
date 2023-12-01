package com.mergeco.oiljang.auth.config;

import com.mergeco.oiljang.auth.filter.CustomAuthenticationFilter;
//import com.mergeco.oiljang.auth.filter.OAuth2AuthenticationFilter;
import com.mergeco.oiljang.auth.handler.*;
/*import lombok.RequiredArgsConstructor;*/
import com.mergeco.oiljang.auth.model.DetailsUser;
import com.mergeco.oiljang.auth.model.service.OAuth2DetailsService;
/*import com.mergeco.oiljang.user.repository.CustomOAuth2AuthorizationRequestRepository;*/
import com.mergeco.oiljang.user.repository.CustomOAuth2AuthorizationRequestRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Collections;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@EnableWebSecurity
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final TokenProvider tokenProvider;

    private final BCryptPasswordEncoder passwordEncoder;

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private final CustomAuthenticationProvider customAuthenticationProvider;

    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final CustomAuthSuccessHandler customAuthSuccessHandler;
    private final CustomAuthFailureHandler customAuthFailureHandler;

    private final OAuth2SuccessHandler oAuth2SuccessHandler;


    private final UserDetailsService userDetailsService;
    private final OAuth2DetailsService oAuth2DetailsService;

    @Autowired
    private OAuth2Config oAuth2Config;



    @Autowired
    public WebSecurityConfig(TokenProvider tokenProvider, BCryptPasswordEncoder passwordEncoder, JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, JwtAccessDeniedHandler jwtAccessDeniedHandler, CustomAuthSuccessHandler customAuthSuccessHandler, CustomAuthFailureHandler customAuthFailureHandler, UserDetailsService userDetailsService, CustomAuthenticationProvider customAuthenticationProvider, OAuth2SuccessHandler oAuth2SuccessHandler, OAuth2DetailsService oAuth2DetailsService) {
        this.tokenProvider = tokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
        this.customAuthSuccessHandler = customAuthSuccessHandler;
        this.customAuthFailureHandler = customAuthFailureHandler;
        this.userDetailsService = userDetailsService;
        this.customAuthenticationProvider = customAuthenticationProvider;
        this.oAuth2SuccessHandler = oAuth2SuccessHandler;

        this.oAuth2DetailsService = oAuth2DetailsService;
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/css/**", "/js/**", "/images/**",
                "/lib/**", "/swagger-ui/index.html", "/swagger-ui.html", "/swagger-ui/**",
                "/webjars/**", "/v2/api-docs", "/configuration/ui", "/swagger-resources/**",
                "/configuration/security");
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.info("Configuring WebSecurity...");

        http
                .cors()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)
                .and()
                .addFilterBefore(customFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers("/", "/login", "/join").permitAll()
                .antMatchers("/users/findId","/users/changPwd").permitAll()
                .antMatchers("/oauth2/**").permitAll()
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .apply(new JwtSecurityConfig(tokenProvider, userDetailsService))
                .and()
                .apply(joinAuthConfigurer())
                .and()
                .apply(new OAuth2Configurer(oAuth2DetailsService, customOAuth2AuthorizationRequestRepository(), authenticationManagerBean()))
                .and()
                .oauth2Login()
                .authorizationEndpoint()
                .baseUri("/oauth2/authorize")
                .authorizationRequestRepository(authorizationRequestRepository())
                .and()
                .userInfoEndpoint()
                .userService(oAuth2DetailsService)
                .and()
                .clientRegistrationRepository(oAuth2Config.clientRegistrationRepository())
                .redirectionEndpoint()
                .baseUri("/oauth2/login/{registrationId}/callback")
                .and()
                .successHandler(oAuth2SuccessHandler)
                .and();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return new ProviderManager(Collections.singletonList(daoAuthenticationProvider()));
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }


    private AuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        provider.setPreAuthenticationChecks((userDetails) -> {
            if ("Y".equals(((DetailsUser) userDetails).getUser().getWithdrawStatus())) {
                throw new DisabledException("탈퇴한 사용자입니다.");
            }
        });
        return provider;
    }


    @Bean
    public CustomAuthenticationFilter customFilter() {
        CustomAuthenticationFilter filter = new CustomAuthenticationFilter(customAuthenticationProvider, oAuth2DetailsService);
        filter.setFilterProcessesUrl("/login");
        filter.setAuthenticationSuccessHandler(customAuthSuccessHandler);
        filter.setAuthenticationFailureHandler(customAuthFailureHandler);
        filter.afterPropertiesSet();
        return filter;
    }

    private class OAuth2Configurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

        private final OAuth2DetailsService oAuth2DetailsService;
        private final CustomOAuth2AuthorizationRequestRepository customOAuth2AuthorizationRequestRepository;

        private AuthenticationManager authenticationManager;


        public OAuth2Configurer(OAuth2DetailsService oAuth2DetailsService, CustomOAuth2AuthorizationRequestRepository customOAuth2AuthorizationRequestRepository, AuthenticationManager authenticationManager) {
            this.oAuth2DetailsService = oAuth2DetailsService;
            this.customOAuth2AuthorizationRequestRepository = customOAuth2AuthorizationRequestRepository;
            this.authenticationManager = authenticationManager;
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            CustomAuthenticationFilter customAuthenticationFilter = customFilter();
            http.addFilterBefore(customAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);        }

        public CustomAuthenticationFilter customFilter() {
            CustomAuthenticationFilter filter = new CustomAuthenticationFilter(customAuthenticationProvider, oAuth2DetailsService);
            filter.setFilterProcessesUrl("/oauth2/login");
            filter.setAuthenticationSuccessHandler(oAuth2SuccessHandler);
            filter.setAuthenticationFailureHandler(customAuthFailureHandler);
            filter.afterPropertiesSet();
            return filter;
        }
    }

    @Bean
    public SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> oAuth2Configurer(
            OAuth2DetailsService oAuth2DetailsService,
            CustomOAuth2AuthorizationRequestRepository customOAuth2AuthorizationRequestRepository,
            AuthenticationManager authenticationManager
    ) {
        return new OAuth2Configurer(oAuth2DetailsService, customOAuth2AuthorizationRequestRepository, authenticationManager);
    }

    public CustomOAuth2AuthorizationRequestRepository customOAuth2AuthorizationRequestRepository() {
        return new CustomOAuth2AuthorizationRequestRepository();
    }

    private AuthorizationRequestRepository<OAuth2AuthorizationRequest> authorizationRequestRepository() {
        return customOAuth2AuthorizationRequestRepository();
    }

    @Bean
    public SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> joinAuthConfigurer() {
        return new SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>() {
            @Override
            public void configure(HttpSecurity http) throws Exception {
                CustomAuthenticationFilter joinAuthFilter = new CustomAuthenticationFilter(customAuthenticationProvider, oAuth2DetailsService);
                joinAuthFilter.setFilterProcessesUrl("/join");
                joinAuthFilter.afterPropertiesSet();
            }
        };
    }

}
