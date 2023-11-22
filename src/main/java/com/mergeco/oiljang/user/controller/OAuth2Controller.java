package com.mergeco.oiljang.user.controller;

import com.mergeco.oiljang.auth.config.OAuth2Config;
import com.mergeco.oiljang.auth.model.DetailsUser;
import com.mergeco.oiljang.common.utils.TokenUtils;
import com.mergeco.oiljang.user.entity.User;
import com.mergeco.oiljang.user.model.service.OAuth2Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.security.SecureRandom;
import java.util.*;

@RestController
@RequestMapping("/oauth2")
@Slf4j
public class OAuth2Controller {

    @Autowired
    private OAuth2AuthorizedClientService oAuth2AuthorizedClientService;

    @Autowired
    private OAuth2Config oAuth2Config;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private OAuth2Service oAuth2Service;


    @PostMapping("/login/google")
    public ResponseEntity<?> loginWithGoogle(){

        log.debug("loginWithGoogle start");

        Map<String, Object> additionalParameters = new HashMap<>();
        additionalParameters.put("access_type", "offline");
        additionalParameters.put("prompt", "consent");

        String authorizeUrl = authorizeUrl("google", oAuth2Config.googleClientRegistration(), additionalParameters);
        return ResponseEntity.ok("{\"authorizeUrl\": \"" + authorizeUrl + "\"}");
    }

    @GetMapping("/login/google/callback")
    public ResponseEntity<?> googleCallback(@RegisteredOAuth2AuthorizedClient("google") OAuth2AuthorizedClient authorizedClient,
                                            @AuthenticationPrincipal OAuth2User oauth2User
    ){
        // OAuth2 사용자 정보를 기반으로 User 생성
        oAuth2Service.joinFromGoogle(oauth2User);


        log.debug("Google Login Successful!");


        // JWT 토큰을 클라이언트에 반환
        return ResponseEntity.ok("Google Login Successful!");
    }


    @PostMapping("/login/naver")
    public ResponseEntity<?> loginWithNaver(){
        Map<String, Object> additionalParameters = new HashMap<>();
        additionalParameters.put("access_type", "offline");
        additionalParameters.put("prompt", "consent");

        String authorizeUrl = authorizeUrl("naver", oAuth2Config.naverClientRegistration(), additionalParameters);
        return ResponseEntity.ok("{\"authorizeUrl\": \"" + authorizeUrl + "\"}");
    }

    @GetMapping("/login/naver/callback")
    public ResponseEntity<?> naverCallback(@RegisteredOAuth2AuthorizedClient("naver") OAuth2AuthorizedClient authorizedClient,
                                            @AuthenticationPrincipal OAuth2User oauth2User){

        // OAuth2 사용자 정보를 기반으로 User 생성
        oAuth2Service.joinFromNaver(oauth2User);


        // JWT 토큰을 클라이언트에 반환
        return ResponseEntity.ok("Naver Login Successful!");
    }

    private String authorizeUrl(String clientRegistrationId, ClientRegistration clientRegistration, Map<String, Object> additionalParameters) {

        String redirectUri = clientRegistration.getRedirectUriTemplate();

        String state = generateRandomString(16);
        String nonce = generateRandomString(16);

        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/oauth2/authorize/" + clientRegistrationId)
                .queryParam("client_id", clientRegistration.getClientId())
                .queryParam("response_type", "code")
                .queryParam("redirect_uri", redirectUri)
                .queryParam("scope", String.join(",", clientRegistration.getScopes()))
                .queryParam("state", state)
                .queryParam("nonce", nonce)
                .build()
                .toUriString();
    }

    // 랜덤 문자열 생성 메소드
    private String generateRandomString(int length) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[length];
        secureRandom.nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }
}
