package com.mergeco.oiljang.user.controller;

import com.mergeco.oiljang.auth.model.dto.JoinDTO;
import com.mergeco.oiljang.user.entity.User;
import com.mergeco.oiljang.user.model.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping("/join")
    public String join(@RequestBody JoinDTO joinDTO) throws Exception {

        JoinDTO value = userService.join(joinDTO);

        if(Objects.isNull(value)){
            return "회원 가입 실패";
        }else {
            return "회원 가입 완료";
        }
    }


    @GetMapping("/test")
    public String test1(){return "test GET";}

    @PostMapping("/test")
    public String test2(){return "test POST";}


}
