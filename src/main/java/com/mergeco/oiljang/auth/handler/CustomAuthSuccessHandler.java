package com.mergeco.oiljang.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mergeco.oiljang.auth.model.DetailsUser;
import com.mergeco.oiljang.auth.model.dto.TokenDTO;
import com.mergeco.oiljang.common.AuthConstants;
import com.mergeco.oiljang.common.restApi.LoginMessage;

import com.mergeco.oiljang.user.entity.User;
import com.mergeco.oiljang.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
@Slf4j
public class CustomAuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;
    private final ModelMapper modelMapper;

    private final UserRepository userRepository;

    public CustomAuthSuccessHandler(TokenProvider tokenProvider, ModelMapper modelMapper, UserRepository userRepository) {
        this.tokenProvider = tokenProvider;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }


    @Override
    @ResponseBody
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        User user = ((DetailsUser) authentication.getPrincipal()).getUser();

        if ("Y".equals(user.getWithdrawStatus()) || "".equals(user.getWithdrawStatus())) {
            LoginMessage loginMessage = new LoginMessage(HttpStatus.FORBIDDEN, "탈퇴한 회원입니다. 로그인이 불가능합니다.", null);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            ObjectMapper objectMapper = new ObjectMapper();
            out.println(objectMapper.writeValueAsString(loginMessage));
            out.flush();
            out.close();
            return;
        }

        TokenDTO tokenDTO = tokenProvider.generateTokenDTO(user);

        log.info("tokenDTO info : {}" , tokenDTO);

        LoginMessage loginMessage = new LoginMessage(HttpStatus.OK, "로그인 성공", tokenDTO);
        response.addHeader(AuthConstants.AUTH_HEADER, AuthConstants.TOKEN_TYPE + " " + tokenDTO.getAccessToken());

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        ObjectMapper objectMapper = new ObjectMapper();

        out.println(objectMapper.writeValueAsString(loginMessage));
        out.flush();
        out.close();

    }

}
