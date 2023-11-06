package com.mergeco.oiljang.user.entity;

import com.mergeco.oiljang.common.UserRole;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "user_info")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Builder
@ToString
public class User {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
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

    @Column(name = "withdraw_status")
    private String withdrawStatus;


    public void authorizeUser() {
        this.role = UserRole.USER;
    }

    public void passwordEncode(PasswordEncoder passwordEncoder) {
        this.pwd= passwordEncoder.encode(this.pwd);
    }


    public void updateRefreshToken(String updateRefreshToken) {
        this.token = updateRefreshToken;
    }

    public List<String> getRoleList() {
        if(this.role.getRole().length() > 0) {
            return Arrays.asList(this.role.getRole().split(","));
        }
        return new ArrayList<>();
    }

}
