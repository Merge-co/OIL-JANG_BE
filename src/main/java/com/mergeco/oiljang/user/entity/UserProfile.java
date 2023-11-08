package com.mergeco.oiljang.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "user_profile")
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_code")
    private int profileCode;

    @OneToOne
    @JoinColumn(name = "ref_user_code", referencedColumnName = "user_code")
    private User refUserCode;

    @Column(name = "user_image_origin_name")
    private String userImageOriginName;

    @Column(name ="user_image_name")
    private String userImageName;

    @Column(name = "user_image_origin_addr")
    private String userImageOriginAddr;

    @Column(name = "user_image_thumb_addr")
    private String userImageThumbAddr;

    public void setRefUserCode(User user) {
        this.refUserCode = user;
    }

}
