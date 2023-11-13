package com.mergeco.oiljang.auth.model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Data
@Builder
public class TokenDTO {
    private String grantType;
    private String memberName;
    private String accessToken;
}
