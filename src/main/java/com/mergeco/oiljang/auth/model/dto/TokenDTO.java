package com.mergeco.oiljang.auth.model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class TokenDTO {
    private String grantType;
    private String memberName;
    private String accessToken;
//    private Long accessTokenExpiresIn;
}
