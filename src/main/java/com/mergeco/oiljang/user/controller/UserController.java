package com.mergeco.oiljang.user.controller;

import com.mergeco.oiljang.auth.model.dto.JoinDTO;
import com.mergeco.oiljang.common.utils.ConvertUtil;
import com.mergeco.oiljang.user.entity.User;
import com.mergeco.oiljang.user.model.dto.UserProfileDTO;
import com.mergeco.oiljang.user.model.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final ConvertUtil userDTOConverter;


    @PostMapping("/users")
    public String join(@RequestBody JoinDTO joinDTO, @RequestBody UserProfileDTO profileDTO, @RequestParam("file") MultipartFile file) throws Exception {



        User value = userService.join(joinDTO,profileDTO,file);

        if(Objects.isNull(value)){
            return "회원 가입 실패";
        }else {
            return "회원 가입 완료";
        }



    }


}
