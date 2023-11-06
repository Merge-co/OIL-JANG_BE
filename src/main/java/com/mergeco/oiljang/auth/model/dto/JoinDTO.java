package com.mergeco.oiljang.auth.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class JoinDTO {

    private String nickname;
    private String name;
    private String id;
    private String pwd;
    private String birthDate;
    private String gender;
    private String enrollType;
    private String phone;
    private String email;

}
