package com.mergeco.oiljang.auth.interceptor;

import com.mergeco.oiljang.common.AuthConstants;
import com.mergeco.oiljang.common.utils.TokenUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.rmi.RemoteException;

public class JwtTokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String header = request.getHeader(AuthConstants.AUTH_HEADER);
        String token = TokenUtils.splitHeader(header);

        if(token != null) {
            if(TokenUtils.isValidToken(token)) {
                return true;
            } else {
                throw new RemoteException("token이 만료 되었습니다.");
            }
        } else {
            throw new RemoteException("token 정보가 없습니다.");
        }
    }
}

