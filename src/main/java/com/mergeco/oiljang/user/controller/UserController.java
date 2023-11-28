package com.mergeco.oiljang.user.controller;


import com.mergeco.oiljang.auth.handler.TokenProvider;
import com.mergeco.oiljang.auth.model.DetailsUser;
import com.mergeco.oiljang.auth.model.dto.JoinDTO;
import com.mergeco.oiljang.common.exception.*;
import com.mergeco.oiljang.common.restApi.LoginMessage;
import com.mergeco.oiljang.common.restApi.ResponseMessage;
import com.mergeco.oiljang.user.entity.User;
import com.mergeco.oiljang.user.model.dto.UpdateUserDTO;
import com.mergeco.oiljang.user.model.dto.UserDTO;
import com.mergeco.oiljang.user.model.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Api(tags = "회원")

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private final UserService userService;

    @ApiOperation(value = "회원가입")
    @PostMapping(value = "/join")
    public ResponseEntity<?> join(@ModelAttribute  JoinDTO joinDTO, @RequestPart(value = "imageFile",required = false) MultipartFile imageFile) {

        log.debug("JoinDTO : {}",joinDTO.getGender());
        log.debug("JoinDTO : {}",joinDTO.getNickname());
        log.debug("imageFile : {}",imageFile);

        try {

            log.debug("joinDTO : {}",joinDTO.getId());
            log.debug("joinDTO : {}",joinDTO.getBirthDate());
            log.debug("joinDTO : {}",joinDTO.getName());
            log.debug("joinDTO : {}",joinDTO.getNickname());
            log.debug("joinDTO : {}",joinDTO.getPwd());
            log.debug("joinDTO : {}",joinDTO.getGender());
            log.debug("imageFile : {}",imageFile);

            User joinUser = userService.join(joinDTO, imageFile);

            return ResponseEntity
                    .ok()
                    .body(new LoginMessage(HttpStatus.OK, "회원 가입 정보", joinUser));
        } catch (DuplicatedException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(400, "Duplicate", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMessage(500, "Server Error", null));
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
    public ResponseEntity<?> checkUserNicknameExist(@RequestParam String nickname) {

        try {
            boolean check = userService.checkUserNicknameExist(nickname);
            if (check) {
                // 사용 가능
                return ResponseEntity.ok().body(new LoginMessage(HttpStatus.OK, "사용 가능한 닉네임입니다.", check));
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
    public ResponseEntity<?> getUserInfo(@PathVariable int userCode) {

        try {
            // 현재 인증된 사용자의 정보
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            log.info("SecurityContextHolder test : {} " , SecurityContextHolder.getContext().getAuthentication());
            log.info("Principal class: {} " , authentication.getPrincipal().getClass());
            log.info("Principal : {} " , authentication.getPrincipal());
            log.info("Authentication: {} " , authentication);

            if (authentication.getPrincipal() instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                DetailsUser authUser = (DetailsUser) userDetails;

                log.debug("authUser 권한 이름 : {} " , authUser.getRole());

                // 현재 인증된 사용자의 userCode와 요청한 userCode를 비교하여 권한 검증
                if (authUser.getUserCode() != userCode) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseMessage(403, "권한이 없습니다.", null));
                }

                // 회원 정보 조회
                UserDTO user = userService.getUserInfo(userCode);

                log.debug("user",user.getBirthDate());

                if (user != null) {
                    return ResponseEntity.ok().body(new LoginMessage(HttpStatus.OK, "회원 정보 조회 성공", user));
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage(404, "회원 정보를 찾을 수 없습니다.", null));
                }
            } else {
                // UserDetails를 구현하지 않은 경우 처리
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseMessage(401, "인증 정보가 유효하지 않습니다.", null));
            }


        } catch (Exception e) {
            log.error("Exception occurred", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessage(500, "서버 오류", null));

        }
    }

    @ApiOperation(value = "나의 회원정보 수정")
    @PostMapping("/users/{userCode}")
    public ResponseEntity<?> updateUserInfo(@PathVariable int userCode, @ModelAttribute UpdateUserDTO updateUserDTO ,@RequestPart(value = "profileImage",required = false) MultipartFile profileImage) {

        log.debug("updateUserInfo start ======");

        log.debug("UpdateUserDTO : {}", updateUserDTO.getNewPassword());
        log.debug("UpdateUserDTO: {}", updateUserDTO.getNickname());
        log.debug("UpdateUserDTO: {}", updateUserDTO.getNewPassword());
        log.debug("profileImage: {}", profileImage);


        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication.getPrincipal() instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                DetailsUser authUser = (DetailsUser) userDetails;

                // 현재 인증된 사용자의 userCode와 요청한 userCode를 비교하여 권한 검증
                if (authUser.getUserCode() != userCode) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseMessage(403, "권한이 없습니다.", null));
                }


                UserDTO updatedUserDTO = userService.updateUser(userCode, updateUserDTO,profileImage);

                if (updatedUserDTO != null) {
                    return ResponseEntity.ok().body(new LoginMessage(HttpStatus.OK, "나의 정보가 업데이트가 되었습니다.", updatedUserDTO));
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage(404, "회원 정보를 찾을 수 없습니다.", null));
                }
            } else {
                // UserDetails를 구현하지 않은 경우 처리
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseMessage(401, "인증 정보가 유효하지 않습니다.", null));
            }

        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage(404, e.getMessage(), null));
        } catch (DuplicatedException | InvalidPasswordException e) {
            return ResponseEntity.badRequest().body(new ResponseMessage(400, e.getMessage(), null));
        } catch (Exception e) {
            log.error("Exception occurred", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMessage(500, "서버 오류", null));
        }
    }

    @ApiOperation(value = "나의 회원 탈퇴")
    @DeleteMapping("/users/{userCode}")
    public ResponseEntity<?> withdrawUser(@PathVariable int userCode){
        try{
            userService.withdrawUser(userCode);
            return ResponseEntity.ok().body(new ResponseMessage(200, "회원 탈퇴가 정상적으로 처리되었습니다.",null));
        }catch (UserNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage(404,e.getMessage(),null));
        }catch (Exception e){
            log.error("Exception occurred", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMessage(500,"서버 오류",null));
        }
    }


}
