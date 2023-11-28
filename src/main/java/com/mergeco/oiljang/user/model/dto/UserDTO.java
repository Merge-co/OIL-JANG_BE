package com.mergeco.oiljang.user.model.dto;

import com.mergeco.oiljang.user.entity.User;
import com.mergeco.oiljang.user.entity.UserProfile;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserDTO {

    private int userCode;
    private String nickname;
    private String name;
    private String id;
    private String pwd;
    private String email;
    private String phone;
    private String birthDate;
    private String withdrawStatus;
    private String profileImageUrl;
    private String userImageOriginName;
    private String userImageName;
    private String userImageOriginAddr;
    private String userImageThumbAddr;

    public static UserDTO fromEntity(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserCode(user.getUserCode());
        userDTO.setNickname(user.getNickname());
        userDTO.setName(user.getName());
        userDTO.setId(user.getId());
        userDTO.setPwd(user.getPwd());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhone(user.getPhone());
        userDTO.setBirthDate(user.getBirthDate());
        userDTO.setWithdrawStatus(user.getWithdrawStatus());
        userDTO.setProfileImageUrl(user.getProfileImageUrl());

        UserProfile userProfile = user.getUserProfile();
        if (userProfile != null) {
            userDTO.setUserImageOriginName(userProfile.getUserImageOriginName());
            userDTO.setUserImageName(userProfile.getUserImageName());
            userDTO.setUserImageOriginAddr(userProfile.getUserImageOriginAddr());
            userDTO.setUserImageThumbAddr(userProfile.getUserImageThumbAddr());
        }

        return userDTO;
    }
}
