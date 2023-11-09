package com.mergeco.oiljang.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mergeco.oiljang.auth.model.DetailsUser;
import com.mergeco.oiljang.auth.model.dto.TokenDTO;
import com.mergeco.oiljang.common.AuthConstants;
import com.mergeco.oiljang.common.restApi.ResponseMessage;
import com.mergeco.oiljang.common.utils.ConvertUtil;
import com.mergeco.oiljang.common.utils.TokenUtils;
import com.mergeco.oiljang.user.entity.User;
import org.json.simple.JSONObject;
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
import java.util.HashMap;

@Component
public class CustomAuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;
    private final ModelMapper modelMapper;

    public CustomAuthSuccessHandler(TokenProvider tokenProvider, ModelMapper modelMapper) {
        this.tokenProvider = tokenProvider;
        this.modelMapper = modelMapper;
    }

    @Override
    @ResponseBody
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        User user = modelMapper.map(authentication.getPrincipal(), User.class);
        TokenDTO tokenDTO = tokenProvider.generateTokenDTO(user);


        HashMap<String, Object> responseResult = new HashMap<>();
        responseResult.put("tokenDTO", tokenDTO);
        ResponseMessage responseMessage = new ResponseMessage(200, "로그인 성공", responseResult);
        response.addHeader(AuthConstants.AUTH_HEADER, AuthConstants.TOKEN_TYPE + " " + tokenDTO.getAccessToken());

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        ObjectMapper objectMapper = new ObjectMapper();

        out.println(objectMapper.writeValueAsString(responseMessage));
        out.flush();
        out.close();

    }

}
