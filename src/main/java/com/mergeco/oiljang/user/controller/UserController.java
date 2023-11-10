package com.mergeco.oiljang.user.controller;


import com.mergeco.oiljang.auth.model.dto.JoinDTO;
import com.mergeco.oiljang.auth.model.dto.LoginDTO;
import com.mergeco.oiljang.common.exception.UserErrorResult;
import com.mergeco.oiljang.common.exception.UserException;
import com.mergeco.oiljang.common.restApi.ResponseMessage;
import com.mergeco.oiljang.user.entity.User;
import com.mergeco.oiljang.user.model.dto.UserProfileDTO;
import com.mergeco.oiljang.user.model.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Api(tags = "회원")

@RestController
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;

    @ApiOperation(value = "회원가입")
    @PostMapping(value = "/users")
    public ResponseEntity<ResponseMessage> join(@ModelAttribute JoinDTO joinDTO, @RequestPart MultipartFile imageFile) throws Exception {

        User value = userService.join(joinDTO,imageFile);

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        HashMap<String, Object> responseResult = new HashMap<>();
        responseResult.put("joinData", value);

        ResponseMessage responseMessage = new ResponseMessage(200, "회원 가입 정보", responseResult);


        if(Objects.isNull(value)){
            return new ResponseEntity<>(responseMessage, headers, HttpStatus.BAD_REQUEST);
        }else {
            return new ResponseEntity<>(responseMessage, headers, HttpStatus.OK);
        }
    }

    public ResponseEntity<ResponseMessage> checkUserIdExist(@PathVariable String userId){
        boolean check = userService.checkUserIdExist(userId);

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));



        if(check == true){
            throw new UserException(UserErrorResult.DUPLICATED_MEMBER_REGISTER);
        }else {
            throw new UserException(UserErrorResult.DUPLICATED_MEMBER_REGISTER);

        }

    }


}
