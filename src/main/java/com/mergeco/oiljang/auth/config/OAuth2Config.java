package com.mergeco.oiljang.auth.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;

import java.util.ArrayList;
import java.util.List;

@Configuration
@Data
@Slf4j
public class OAuth2Config {

    private final Auth auth = new Auth();
    private final OAuth2 oauth2 = new OAuth2();

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String googleClientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String googleClientSecret;

    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String googleRedirectUri;

    @Value("${spring.security.oauth2.client.registration.google.scope}")
    private String googleScope;

    @Value("${spring.security.oauth2.client.registration.naver.client-id}")
    private String naverClientId;

    @Value("${spring.security.oauth2.client.registration.naver.client-secret}")
    private String naverClientSecret;

    @Value("${spring.security.oauth2.client.registration.naver.redirect-uri}")
    private String naverRedirectUri;

    @Value("${spring.security.oauth2.client.registration.naver.scope}")
    private String naverScope;

    @Value("${spring.security.oauth2.client.provider.naver.authorization-uri}")
    private String naverAuthorizationUri;

    @Value("${spring.security.oauth2.client.provider.naver.token_uri}")
    private String naverTokenUri;

    @Value("${spring.security.oauth2.client.provider.naver.user-info-uri}")
    private String naverUserInfoUri;

    @Value("${spring.security.oauth2.client.provider.naver.user_name_attribute}")
    private String naverUserNameAttribute;



    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(
                googleClientRegistration(),
                naverClientRegistration()
        );
    }

    public ClientRegistration googleClientRegistration() {
        log.debug("googleClientId : {}",googleClientId);
        log.debug("googleClientSecret : {}",googleClientSecret);
        log.debug("googleRedirectUri : {}",googleRedirectUri);
        log.debug("googleScope : {}",googleScope);

        return ClientRegistration.withRegistrationId("google")
                .clientId(googleClientId)
                .clientSecret(googleClientSecret)
                .redirectUri(googleRedirectUri)
                .scope(googleScope.split(","))
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .clientName("Google")
                .authorizationUri("https://accounts.google.com/o/oauth2/auth")
                .tokenUri("https://accounts.google.com/o/oauth2/token")
                .userInfoUri("https://www.googleapis.com/oauth2/v3/userinfo")
                .jwkSetUri("https://www.googleapis.com/oauth2/v3/certs")
                .userNameAttributeName(IdTokenClaimNames.SUB)
                .clientAuthenticationMethod(ClientAuthenticationMethod.BASIC)
                .build();


    }

    public ClientRegistration naverClientRegistration() {

        log.debug("naverClientId : {}",naverClientId);
        log.debug("naverClientSecret : {}",naverClientSecret);
        log.debug("naverRedirectUri : {}",naverRedirectUri);
        log.debug("naverScope : {}",naverScope);
        log.debug("naverAuthorizationUri : {}",naverAuthorizationUri);
        log.debug("naverTokenUri : {}",naverTokenUri);
        log.debug("naverUserInfoUri : {}",naverUserInfoUri);

        return ClientRegistration.withRegistrationId("naver")
                .clientId(naverClientId)
                .clientSecret(naverClientSecret)
                .redirectUri(naverRedirectUri)
                .scope(naverScope.split(","))
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationUri(naverAuthorizationUri)
                .tokenUri(naverTokenUri)
                .userInfoUri(naverUserInfoUri)
                .userNameAttributeName(naverUserNameAttribute)
                .clientName("Naver")
                .build();
    }


    @Data
    public static class Auth {
        private String tokenSecret;
        private long accessTokenExpirationMsec;
        private long refreshTokenExpirationMsec;
    }

    @Data
    public static final class OAuth2 {
        private List<String> authorizedRedirectUris = new ArrayList<>();
    }
}
