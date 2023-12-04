package com.mergeco.oiljang.userSanctions.controller;


import com.mergeco.oiljang.common.restApi.LoginMessage;
import com.mergeco.oiljang.userSanctions.repository.UserSanctionsRepository;
import com.mergeco.oiljang.userSanctions.service.UserSanctionsService;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sanctions")
@Slf4j
public class UserSanctionsController {

    private final UserSanctionsService userSanctionsService;

    public UserSanctionsController(UserSanctionsService userSanctionsService) {
        this.userSanctionsService = userSanctionsService;
    }
    @ApiOperation(value = "사용자제제", notes = "사용자 제제조회", tags = {"SanctionsController"})
    @GetMapping("/sanction")
    public ResponseEntity<?> selectSanctions () {
        log.info("[SanctionsController] selectSanctions Start ==================");
        log.info("[SanctionsController] selectSanctions END ==================");

        return ResponseEntity.ok().body(new LoginMessage(HttpStatus.OK, "조회 성공", userSanctionsService.selectUserSanctions()));
    }
}
