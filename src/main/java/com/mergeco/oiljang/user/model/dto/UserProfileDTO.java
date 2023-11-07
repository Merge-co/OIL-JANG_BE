package com.mergeco.oiljang.user.model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserProfileDTO {
    private String userImageOriginName;
    private String userImageName;
    private String userImageOriginAddr;
    private String userImageThumbAddr;
}
