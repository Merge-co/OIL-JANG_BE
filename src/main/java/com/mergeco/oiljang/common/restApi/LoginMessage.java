package com.mergeco.oiljang.common.restApi;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;


@Getter
@Setter
@ToString
public class LoginMessage {

    private int status;

    private String message;

    private Object data;

    public LoginMessage(HttpStatus status, String message, Object data) {
        this.status = status.value();
        this.message = message;
        this.data = data;
    }

}
