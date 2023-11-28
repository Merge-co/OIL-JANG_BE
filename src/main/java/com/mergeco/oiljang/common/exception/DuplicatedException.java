package com.mergeco.oiljang.common.exception;

public class DuplicatedException extends RuntimeException{
    public DuplicatedException(String message){
        super(message);
    }
}
