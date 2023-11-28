package com.mergeco.oiljang.user.model.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UpdateUserDTO {
    private String nickname;
    private String newPassword;
    private String newPasswordConfirm;
}
