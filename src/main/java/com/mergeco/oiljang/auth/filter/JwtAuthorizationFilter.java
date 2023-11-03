/*
package com.mergeco.oiljang.auth.filter;

import com.mergeco.oiljang.auth.model.DetailsUser;
import com.mergeco.oiljang.common.AuthConstants;
import com.mergeco.oiljang.common.utils.TokenUtils;
import com.mergeco.oiljang.user.entity.User;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterNestedErrorDispatch(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        List<String> roleLessList = Arrays.asList("/join");

        if(roleLessList.contains((request.getRequestURI()))){
            chain.doFilter(request, response);
            return;
        }

        String header = request.getHeader(AuthConstants.AUTH_HEADER);

        try {
            if(header != null && !header.equalsIgnoreCase("")){
                String token = TokenUtils.splitHeader(header);

                if(TokenUtils.isValidToken(token)){
                    Claims claims = TokenUtils.getClaimsFromToken(token);

                    DetailsUser authentication = new DetailsUser();
                    User user = new User();
                }
            }
        }
    }
}
*/
