package com.mergeco.oiljang.user.controller;


import com.mergeco.oiljang.auth.model.dto.JoinDTO;
import com.mergeco.oiljang.user.entity.User;
import com.mergeco.oiljang.user.model.dto.UserProfileDTO;
import com.mergeco.oiljang.user.model.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;

    private CommonsMultipartResolver resolver;

    @PostMapping(value = "/users")
    public ResponseEntity<?> join(@RequestPart("joinDTO") JoinDTO joinDTO, @RequestPart UserProfileDTO profileDTO, @RequestPart MultipartFile file) throws Exception {


        User value = userService.join(joinDTO,profileDTO,file);

        if(Objects.isNull(value)){
            return ResponseEntity.ok().build();
        }else {
            return ResponseEntity.ok().build();
        }
    }
}
