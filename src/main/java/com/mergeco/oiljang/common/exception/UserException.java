package com.mergeco.oiljang.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserException extends RuntimeException{

    private final UserErrorResult errorResult;
}
