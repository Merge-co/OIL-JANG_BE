package com.mergeco.oiljang.user.controller;


import com.mergeco.oiljang.auth.handler.TokenProvider;
import com.mergeco.oiljang.auth.model.dto.JoinDTO;
import com.mergeco.oiljang.common.exception.DuplicateNicknameException;
import com.mergeco.oiljang.common.exception.InvalidPasswordException;
import com.mergeco.oiljang.common.exception.UserException;
import com.mergeco.oiljang.common.restApi.LoginMessage;
import com.mergeco.oiljang.common.restApi.ResponseMessage;
import com.mergeco.oiljang.user.entity.User;
import com.mergeco.oiljang.user.model.dto.UpdateUserDTO;
import com.mergeco.oiljang.user.model.dto.UserDTO;
import com.mergeco.oiljang.user.model.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Api(tags = "회원")

@RestController
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private final UserService userService;

    @ApiOperation(value = "회원가입")
    @PostMapping(value = "/users")
    public ResponseEntity<?> join(@ModelAttribute JoinDTO joinDTO, @RequestPart MultipartFile imageFile) {

        try {
            User joinUser = userService.join(joinDTO, imageFile);

            return ResponseEntity
                    .ok()
                    .body(new LoginMessage(HttpStatus.OK, "회원 가입 정보", joinUser));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessage(500, "서버 오류", null));
        }

    }

    @ApiOperation(value = "중복 체크")
    @GetMapping("/users/checkId")
    public ResponseEntity<ResponseMessage> checkUserIdExist(@RequestParam String id) {

        try {
            boolean check = userService.checkUserIdExist(id);
            if (check) {
                // 사용 가능
                return ResponseEntity.ok().body(new ResponseMessage(200, "사용 가능한 ID입니다.", null));
            } else {

                //중복된 경우
                return ResponseEntity.ok().body(new ResponseMessage(200, "중복된 ID입니다.", null));
            }
        } catch (UserException e) {
            // 예외가 발생한 경우
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessage(500, "서버 오류", null));
        }

    }

    @ApiOperation(value = "닉네임 중복 체크")
    @GetMapping("/users/checkNickname")
    public ResponseEntity<ResponseMessage> checkUserNicknameExist(@RequestParam String nickname) {

        try {
            boolean check = userService.checkUserNicknameExist(nickname);
            if (check) {
                // 사용 가능
                return ResponseEntity.ok().body(new ResponseMessage(200, "사용 가능한 닉네임입니다.", null));
            } else {

                //중복된 경우
                return ResponseEntity.ok().body(new ResponseMessage(200, "중복된 닉네임입니다.", null));
            }
        } catch (UserException e) {
            // 예외가 발생한 경우
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessage(500, "서버 오류", null));
        }

    }


    @ApiOperation(value = "나의 회원정보 조회")
    @GetMapping("/users/{userCode}")
    public ResponseEntity<?> getUserInfo(@PathVariable int userCode, Authentication authentication) {

        try {
            //현재 인증된 사용자의 정보
            User authUser = (User) authentication.getPrincipal();

            // 현재 인증된 사용자의 userCode와 요청한 userCode를 비교하여 권한 검증
            if (authUser.getUserCode() != userCode) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseMessage(403, "권한이 없습니다.", null));
            }

            // 회원 정보 조회
            UserDTO user = userService.getUserInfo(userCode);

            if (user != null) {
                return ResponseEntity.ok().body(new LoginMessage(HttpStatus.OK, "회원 정보 조회 성공", user));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage(404, "회원 정보를 찾을 수 없습니다.", null));
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessage(500, "서버 오류", null));

        }
    }

    @ApiOperation(value = "나의 회원정보 수정")
    @PostMapping("/users/{userCode}")
    public ResponseEntity<?> updateUserInfo(@PathVariable int userCode, @ModelAttribute UpdateUserDTO updateUserDTO) {

        try {
            UserDTO updatedUserDTO = userService.updateUser(userCode, updateUserDTO);
            return ResponseEntity.ok().body(new LoginMessage(HttpStatus.OK, "나의 정보가 업데이트가 되었습니다.", updatedUserDTO));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage(404, e.getMessage(), null));
        } catch (DuplicateNicknameException | InvalidPasswordException e) {
            return ResponseEntity.badRequest().body(new ResponseMessage(400, e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMessage(500, "서버 오류", null));
        }
    }

}
