package com.mergeco.oiljang.auth.model.dto;

import com.mergeco.oiljang.common.UserRole;
import com.mergeco.oiljang.user.entity.EnrollType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class JoinDTO {

    private String nickname;
    private String name;
    private String id;
    private String pwd;
    private String birthDate;
    private String gender;
    private EnrollType enrollType;
    private UserRole role;
    private String phone;
    private String email;
    private String verifyStatus;
    private String profileImageUrl;

}
