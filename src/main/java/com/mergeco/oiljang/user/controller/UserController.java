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

@Api(tags = "회원")

@RestController
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;

    @ApiOperation(value = "회원가입")
    @PostMapping(value = "/users")
    public ResponseEntity<ResponseMessage> join(@ModelAttribute JoinDTO joinDTO, @RequestPart MultipartFile imageFile) {

        try {
            userService.join(joinDTO, imageFile);

            return ResponseEntity
                    .ok()
                    .body(new ResponseMessage(200, "회원 가입 정보", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessage(500, "서버 오류", null));
        }

    }
    @ApiOperation(value = "아이디 중복 체크")
    @GetMapping("/users")
    public ResponseEntity<ResponseMessage> checkUserIdExist(@RequestParam String id) {

        try {
        boolean check = userService.checkUserIdExist(id);
            if (check) {
                // 사용 가능
                return ResponseEntity.ok().body(new ResponseMessage(200, "사용 가능한 ID입니다.", null));
            } else {

                //중복된 경우
                return ResponseEntity.ok().body(new ResponseMessage(200,"중복된 ID입니다.",null));
            }
        }catch (UserException e){
            // 예외가 발생한 경우
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessage(500,"서버 오류",null));
        }

    }


}
