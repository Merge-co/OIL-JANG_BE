package com.mergeco.oiljang.user.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserDTO {

    private String nickname;
    private String name;
    private String id;
    private String pwd;
    private String email;
    private String phone;
    private String profileImageUrl;

}
