package com.mergeco.oiljang.user.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mergeco.oiljang.common.UserRole;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "user_info")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Builder
@ToString
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_code")
    private int userCode;

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
    @ColumnDefault("NORMAL")
    private EnrollType enrollType;

    @Enumerated(EnumType.STRING)
    @Column(name = "auth_name")
    @ColumnDefault("ROLE_USER")
    private UserRole role;

    @Column(name = "verify_status")
    private String verifyStatus;

    @Column(name = "withdraw_status")
    private String withdrawStatus;

    @Column(name = "enroll_date")
    @DateTimeFormat(pattern = "YYYY-MM-DD hh:mm:ss")
    private LocalDateTime enrollDate;

    @JsonIgnore
    @OneToOne(mappedBy = "refUserCode", cascade = CascadeType.ALL)
    @JsonManagedReference
    private UserProfile userProfile;


    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
        if (userProfile != null) {
            userProfile.setRefUserCode(this);
        }
    }

    public void withdraw() {
        this.withdrawStatus = "Y";
    }

    public void authorizeUser() {
        this.role = UserRole.USER;
    }

    public void passwordEncode(PasswordEncoder passwordEncoder) {
        this.pwd= passwordEncoder.encode(this.pwd);
    }


    public List<String> getRoleList() {
        if(this.role.getRole().length() > 0) {
            return Arrays.asList(this.role.getRole().split(","));
        }
        return new ArrayList<>();
    }

}
