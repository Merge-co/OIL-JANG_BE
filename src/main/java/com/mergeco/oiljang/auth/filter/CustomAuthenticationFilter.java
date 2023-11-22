package com.mergeco.oiljang.auth.filter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mergeco.oiljang.auth.handler.CustomAuthenticationProvider;
import com.mergeco.oiljang.auth.model.DetailsUser;
import com.mergeco.oiljang.auth.model.service.OAuth2DetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final CustomAuthenticationProvider customAuthenticationProvider;
    private final OAuth2DetailsService oAuth2DetailsService;


    public CustomAuthenticationFilter(CustomAuthenticationProvider customAuthenticationProvider,
                                      OAuth2DetailsService oAuth2DetailsService) {
        this.oAuth2DetailsService = oAuth2DetailsService;
        super.setAuthenticationManager(new ProviderManager(Collections.singletonList(customAuthenticationProvider)));
        this.customAuthenticationProvider = customAuthenticationProvider;
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("Processing CustomAuthenticationFilter...");

        if (!isOAuth2LoginRequest(request)) {
            UsernamePasswordAuthenticationToken authRequest;

            try {
                log.info("Regular Login Request detected.");
                authRequest = getAuthRequest(request);
                setDetails(request, authRequest);
                log.info("Authentication request: {}", authRequest);
                log.info("Attempting authentication with provided credentials.");

                Authentication authenticationResult = this.getAuthenticationManager().authenticate(authRequest);

                if (authenticationResult.isAuthenticated()) {
                    log.info("Authentication successful for user: {}", authenticationResult.getPrincipal());
                } else {
                    log.info("Authentication failed for user: {}", authRequest.getPrincipal());
                }
                return authenticationResult;

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                return attemptOAuth2Authentication(request);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    private Authentication attemptOAuth2Authentication(HttpServletRequest request) throws IOException {
        log.info("Attempting OAuth2 authentication...");

        try {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);

        DetailsUser user = objectMapper.readValue(request.getInputStream(), DetailsUser.class);
        log.info("User details received: {}", user);

        DetailsUser userDetails = (DetailsUser) oAuth2DetailsService.loadUserByOAuth2Info(user.getId());


        if (userDetails == null) {
            // OAuth2 사용자 정보로 UserDetails를 가져오지 못한 경우에 대한 처리
            throw new AuthenticationServiceException("OAuth2 user details not found");
        }

        return super.getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities())
        );
        } catch (Exception e) {
            log.debug("OAuth2AuthenticationFilter attemptAuthentication error", e);
            throw new AuthenticationServiceException("Failed to authenticate with OAuth2");

        }
    }


    private UsernamePasswordAuthenticationToken getAuthRequest(HttpServletRequest request) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);

        DetailsUser user = objectMapper.readValue(request.getInputStream(), DetailsUser.class);
        log.info("User details received: {}", user);

        List<GrantedAuthority> authorities = new ArrayList<>(user.getAuthorities());
        log.info("Authorities from DetailsUser: {}", authorities);


        DetailsUser userDetails = new DetailsUser(
                user.getUserCode(),
                user.getNickname(),
                user.getId(),
                user.getPwd(),
                user.getName(),
                user.getEmail(),
                user.getBirthDate(),
                user.getGender(),
                user.getPhone(),
                user.getRole(),
                user.getUser(),
                user.getAuthorities()
        );

        log.info("UserDetails created: {}", userDetails);



        return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), user.getAuthorities());

    }

    private boolean isOAuth2LoginRequest(HttpServletRequest request) {
        return request.getRequestURI().startsWith("/oauth2/**") || request.getRequestURI().equals("/oauth2/login");
    }

}





