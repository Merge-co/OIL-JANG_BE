package com.mergeco.oiljang.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mergeco.oiljang.auth.model.dto.LoginDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @DisplayName("JWT 로그인 성공 테스트")
    public void testJwtLoginSuccess() throws Exception {

        /*String id = "user01";
        String pwd = "user01";*/
        String id = "test01";
        String pwd = "1234";

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setId(id);
        loginDTO.setPwd(pwd);


        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(loginDTO)))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.accessToken").exists())
                .andReturn();
    }

    @Test
    @DisplayName("JWT 로그인 실패 테스트 (잘못된 비밀번호)")
    public void testJwtLoginWrongPwdFailure() throws Exception {

        String id = "user01";
        String pwd = "wrong_password";

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setId(id);
        loginDTO.setPwd(pwd);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.failType").value("아이디 또는 비밀번호가 틀립니다."))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();

    }

   /* @Test
    @DisplayName("JWT 로그인 실패 테스트 (존재하지 않은 회원)")
    public void testJwtLoginWrongIdFailure() throws Exception {

        String id = "wrong_password";
        String pwd = "wrong_password";

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setId(id);
        loginDTO.setPwd(pwd);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.failType").value("존재하지 않는 사용자입니다."))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();

    }*/

    }
