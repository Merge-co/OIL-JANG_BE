package com.mergeco.oiljang.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorResult {

    DUPLICATED_MEMBER_REGISTER(HttpStatus.BAD_REQUEST, "중복된 ID입니다."),
    DUPLICATED_NICKNAME_REGISTER(HttpStatus.BAD_REQUEST, "중복된 닉네임입니다."),
    DUPLICATED_PHONE_REGISTER(HttpStatus.BAD_REQUEST, "이미 등록된 번호입니다."),
    NO_USER_ID(HttpStatus.BAD_REQUEST, "아이디가 존재하지 않습니다."),
    NO_PWD_CORRECT(HttpStatus.BAD_REQUEST,"비밀번호가 틀렸습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
