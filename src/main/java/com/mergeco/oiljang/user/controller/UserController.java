package com.mergeco.oiljang.user.controller;


import com.mergeco.oiljang.auth.model.dto.JoinDTO;
import com.mergeco.oiljang.common.restApi.ResponseMessage;
import com.mergeco.oiljang.user.entity.User;
import com.mergeco.oiljang.user.model.dto.UserProfileDTO;
import com.mergeco.oiljang.user.model.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Objects;

@Api(tags = "회원")

@RestController
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;

    private CommonsMultipartResolver resolver;

    @ApiOperation(value = "신규 회원 추가")
    @PostMapping(value = "/users")
    public ResponseEntity<ResponseMessage> join(@ModelAttribute JoinDTO joinDTO, @RequestPart MultipartFile imageFile) throws Exception {

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        User value = userService.join(joinDTO,imageFile);

        if(Objects.isNull(value)){
            return ResponseEntity.ok().build();
        }else {
            return ResponseEntity.ok().build();
        }
    }
}
