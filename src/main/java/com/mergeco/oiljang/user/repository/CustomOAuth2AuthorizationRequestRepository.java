
package com.mergeco.oiljang.user.repository;

import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
public class CustomOAuth2AuthorizationRequestRepository implements AuthorizationRequestRepository<OAuth2AuthorizationRequest> {

    private static final String REDIRECT_URI_PARAM_COOKIE_NAME = "redirect_uri";


    private final HttpSessionOAuth2AuthorizationRequestRepository delegate = new HttpSessionOAuth2AuthorizationRequestRepository();


    @Override
    public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {

        OAuth2AuthorizationRequest authorizationRequest = delegate.loadAuthorizationRequest(request);

        if (authorizationRequest != null) {
            Map<String, Object> additionalParameters = authorizationRequest.getAdditionalParameters();
            if (additionalParameters != null && additionalParameters.containsKey(REDIRECT_URI_PARAM_COOKIE_NAME)) {
                String redirectUri = additionalParameters.get(REDIRECT_URI_PARAM_COOKIE_NAME).toString();
            }
        }

        return authorizationRequest;
    }

    @Override
    public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest, HttpServletRequest request, HttpServletResponse response) {
        if (authorizationRequest == null) {
            this.removeAuthorizationRequest(request, response);
            return;
        }

        delegate.saveAuthorizationRequest(authorizationRequest, request, response);
    }

    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request) {
        return null;
    }

    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request, HttpServletResponse response) {
        return delegate.removeAuthorizationRequest(request, response);

    }

    private void removeAuthorizationRequest(HttpServletRequest request, HttpServletResponse response, String redirectUriCookieName) {
        delegate.removeAuthorizationRequest(request);

        if (redirectUriCookieName != null) {
            javax.servlet.http.Cookie redirectUriCookie = new javax.servlet.http.Cookie(redirectUriCookieName, null);
            redirectUriCookie.setMaxAge(0);
            redirectUriCookie.setPath(getCookiePath(request));
            response.addCookie(redirectUriCookie);
        }
    }

    private String getCookiePath(HttpServletRequest request) {
        return request.getContextPath();
    }
}
