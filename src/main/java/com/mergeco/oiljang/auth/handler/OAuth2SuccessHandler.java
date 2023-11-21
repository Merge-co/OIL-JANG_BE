package com.mergeco.oiljang.auth.handler;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.mergeco.oiljang.auth.model.DetailsUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final TokenProvider tokenProvider;

    public OAuth2SuccessHandler(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, IOException {
        DetailsUser userDetails = (DetailsUser) authentication.getPrincipal();
        String token = tokenProvider.generateTokenDTO(userDetails.getUser()).getAccessToken();

        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("accessToken", token);
        responseData.put("userDetails", buildUserDetailsMap(userDetails));

        response.getWriter().write(objectMapper.writeValueAsString(responseData));
    }

    private Map<String, Object> buildUserDetailsMap(DetailsUser userDetails) {
        Map<String, Object> userDetailsMap = new HashMap<>();
        userDetailsMap.put("userCode", userDetails.getUserCode());
        userDetailsMap.put("nickname", userDetails.getNickname());
        userDetailsMap.put("id", userDetails.getId());
        userDetailsMap.put("name", userDetails.getName());
        userDetailsMap.put("email", userDetails.getEmail());
        userDetailsMap.put("roles", userDetails.getAuthorities()
                .stream()
                .map(authority -> ((SimpleGrantedAuthority) authority).getAuthority())
                .collect(Collectors.toList()));
        return userDetailsMap;
    }
}
