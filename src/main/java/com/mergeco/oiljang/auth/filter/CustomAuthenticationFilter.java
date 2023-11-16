package com.mergeco.oiljang.auth.filter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mergeco.oiljang.auth.handler.CustomAuthenticationProvider;
import com.mergeco.oiljang.auth.model.DetailsUser;
import lombok.extern.slf4j.Slf4j;
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
import java.util.List;

@Component
@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private CustomAuthenticationProvider customAuthenticationProvider;

    public CustomAuthenticationFilter(CustomAuthenticationProvider customAuthenticationProvider) {
        super.setAuthenticationManager(new ProviderManager(customAuthenticationProvider));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("Processing CustomAuthenticationFilter...");

        if (!shouldAuthenticate(request)) {
            return null;
        }

        UsernamePasswordAuthenticationToken authRequest;

        try {
            authRequest = getAuthRequest(request);
            setDetails(request,authRequest);
            log.info("Authentication request: {}", authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);
        }catch (IOException e){
            throw new RuntimeException(e);
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

    private boolean shouldAuthenticate(HttpServletRequest request) {
        return !"/join".equals(request.getRequestURI());
    }


}





