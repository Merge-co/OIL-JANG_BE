package com.mergeco.oiljang.auth.filter;

import com.mergeco.oiljang.auth.handler.TokenProvider;
import com.mergeco.oiljang.common.AuthConstants;
import com.mergeco.oiljang.common.exception.TokenException;
import com.mergeco.oiljang.common.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;

    private final UserDetailsService userDetailsService;


    public JwtFilter(TokenProvider tokenProvider, UserDetailsService userDetailsService) {
        this.tokenProvider = tokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        log.info("doFilterInternal start");
        log.info("request : {}", request);

        try {
            // 1. Request에서 토큰 추출
            String jwt = resolveToken(request);
            log.debug("resolveToken(request): {}", jwt);
            log.debug("StringUtils.hasText(jwt): {}", StringUtils.hasText(jwt));
            log.debug("TokenUtils.isValidToken(jwt): {}", TokenUtils.isValidToken(jwt));
            if (StringUtils.hasText(jwt) && TokenUtils.isValidToken(jwt)&& !isJoinEndpoint(request)) {
                // 2. 토큰이 유효하면 사용자 정보 가져오기

                // 2.1. 토큰과 UserDetailsService를 사용하여 Authentication 객체 생성
                Authentication authentication = tokenProvider.getAuthentication(jwt, userDetailsService);
                log.debug("Authentication from tokenProvider: {}", authentication);

                // 디버깅을 위한 출력
                log.debug("Attempting to authenticate user with token.");
                log.debug("Authentication set in SecurityContextHolder: {} ", authentication);
                log.debug("Current Authentication in SecurityContextHolder: {} ", SecurityContextHolder.getContext().getAuthentication());

                // 3. Authentication 객체 생성
                SecurityContextHolder.getContext().setAuthentication(authentication);

                // 디버깅을 위한 출력
                log.info("Successfully authenticated user with token.");
            }
        } catch (TokenException e) {
            log.error("An error occurred during authentication:", e);
        }
        // 4. 다음 필터로 이동
        filterChain.doFilter(request, response);
    }


    private String resolveToken(HttpServletRequest request) {

        if (request == null) {
            log.error("HttpServletRequest is null");
            return null;
        }


        String bearerToken = request.getHeader(AuthConstants.AUTH_HEADER.toLowerCase());

        if (bearerToken == null || !bearerToken.startsWith(AuthConstants.TOKEN_TYPE)) {
            log.debug("Authorization 헤더가 유효하지 않습니다.");
            return null;
        }


        log.debug("Authorization 헤더: {}", bearerToken);
        log.debug("StringUtils.hasText(bearerToken) : {}", StringUtils.hasText(bearerToken));
        log.debug("bearerToken.startsWith(AuthConstants.TOKEN_TYPE) : {}", bearerToken.startsWith(AuthConstants.TOKEN_TYPE));

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(AuthConstants.TOKEN_TYPE)) {
            return TokenUtils.splitHeader(bearerToken); // 사용자가 보낸 토큰 값 추출
        }

        return null;
    }

    private boolean isJoinEndpoint(HttpServletRequest request) {
        return request.getRequestURI().equals("/join") && request.getMethod().equals("POST");
    }
}
