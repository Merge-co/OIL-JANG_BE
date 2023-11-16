package com.mergeco.oiljang.auth.config;

import com.mergeco.oiljang.auth.filter.JwtFilter;
import com.mergeco.oiljang.auth.handler.TokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final TokenProvider tokenProvider;

    private final UserDetailsService userDetailsService;


    public JwtSecurityConfig(TokenProvider tokenProvider, UserDetailsService userDetailsService) {
        this.tokenProvider = tokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        JwtFilter customFilter = new JwtFilter(tokenProvider,userDetailsService);
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);

        log.info("JwtSecurityConfig has been configured.");

    }
}
