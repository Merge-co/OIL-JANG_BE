package com.mergeco.olijang.user.entity;

import com.mergeco.olijang.common.UserRole;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "user_info")
@Getter
@AllArgsConstructor
public class User {

    @Id
    @Column(name = "user_code", columnDefinition = "BINARY(16)")
    private UUID userId;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "name")
    private String name;

    @Column(name = "id")
    private String id;

    @Column(name = "pwd")
    private String pwd;

    @Column(name = "birthdate")
    private String birthDate;

    @Column(name = "gender")
    private String gender;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @CreatedDate
    @Column(name = "enroll_date")
    private LocalDateTime enrollDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "enroll_type")
    private EnrollType enrollType;

    @Column(name = "token")
    private String token;

    @Enumerated(EnumType.STRING)
    @Column(name = "auth_name")
    private UserRole role;

    @Column(name = "verify_status")
    private String verifyStatus;

    /*객체 생성 시 탈퇴 여부 디폴트로 "N" 설정*/
    @Builder.Default
    @Column(name = "withdraw_status")
    private String withdrawStatus = "N";


    public List<String> getRoleList() {
        if(this.role.getRole().length() > 0) {
            return Arrays.asList(this.role.getRole().split(","));
        }
        return new ArrayList<>();
    }

}
