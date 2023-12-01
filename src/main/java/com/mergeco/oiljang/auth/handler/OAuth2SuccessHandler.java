package com.mergeco.oiljang.auth.handler;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.mergeco.oiljang.auth.model.DetailsUser;
import com.mergeco.oiljang.auth.model.dto.TokenDTO;
import com.mergeco.oiljang.common.AuthConstants;
import com.mergeco.oiljang.common.UserRole;
import com.mergeco.oiljang.common.restApi.LoginMessage;
import com.mergeco.oiljang.user.entity.User;
import com.mergeco.oiljang.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    private DetailsUser detailsUser;

    public OAuth2SuccessHandler(TokenProvider tokenProvider,
                                UserRepository userRepository) {
        this.tokenProvider = tokenProvider;
        this.userRepository = userRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, IOException {
        if (authentication.getPrincipal() instanceof DefaultOAuth2User) {

            log.debug("request : {}", request);
            log.debug("response : {}", response);


            DefaultOAuth2User userDetails = (DefaultOAuth2User) authentication.getPrincipal();

            log.debug("userDetails info : {}", userDetails);

            log.debug("google userDetails getName : {}", userDetails.getName());
            log.debug("naver userDetails getName : {}", userDetails.getName());

            Object nameAttribute = userDetails.getAttribute("name");
            log.debug("nameAttribute : {}", nameAttribute);
            //log.debug("nameAttribute type : {}", nameAttribute.getClass());

            if (nameAttribute instanceof String || nameAttribute != null) {

                log.debug("google start~~~~~");

                // 구글 로그인일 경우
                String email = userDetails.getAttribute("email");
                User user = userRepository.findByEmailFromOAuth2(email);

                log.debug("detailsUser info: {}", user);

                if (user != null) {
                    TokenDTO token = tokenProvider.generateTokenDTO(user);

                    log.debug("token info: {}", token);

                    Cookie cookie = new Cookie("accessToken", token.getAccessToken());
                    cookie.setPath("/");
                    cookie.setMaxAge(7 * 24 * 60 * 60); // 쿠키 만료 시간 (초)
                    response.addCookie(cookie);

                    // 응답 헤더 설정
                    response.addHeader("accessToken", token.getAccessToken());
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.setContentType("application/json;charset=UTF-8");

                    response.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE);
                    response.getWriter().write("<script>window.close(); window.opener.location.href='http://localhost:3000'; window.location.reload();</script>");

                } else {
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    response.getWriter().write("Authentication failed");
                }


            } else if (nameAttribute instanceof List || nameAttribute == null) {

                log.debug("naver start~~~~~");

                String id = ((Map<String, Object>) userDetails.getAttribute("response")).get("id").toString();
                User user = userRepository.findByUserId(id);

                log.debug("detailsUser info: {}", user);

                if (user != null) {
                    // user가 null이 아닌 경우에 대한 로직 추가
                    TokenDTO token = tokenProvider.generateTokenDTO(user);

                    log.debug("token info: {}", token);

                    Cookie cookie = new Cookie("accessToken", token.getAccessToken());
                    cookie.setPath("/");
                    cookie.setMaxAge(7 * 24 * 60 * 60); // 쿠키 만료 시간 (초)
                    response.addCookie(cookie);

                    // 응답 헤더 설정
                    response.addHeader("accessToken", token.getAccessToken());
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.setContentType("application/json;charset=UTF-8");

                    response.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE);
                    response.getWriter().write("<script>window.close(); window.opener.location.href='http://localhost:3000'; window.location.reload();</script>");
                } else {
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    response.getWriter().write("Authentication failed");
                }

            } else {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.getWriter().write("Authentication failed");
            }


        }
    }
}
