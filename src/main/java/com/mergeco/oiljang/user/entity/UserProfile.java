package com.mergeco.oiljang.user.entity;

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
    @JoinColumn(name = "ref_user_code", referencedColumnName = "user_Code")
    private User user;

    @Column(name = "user_image_origin_name")
    private String userImageOriginName;

    @Column(name ="user_image_name")
    private String userImageName;

    @Column(name = "user_image_origin_addr")
    private String userImageOriginAddr;

    @Column(name = "user_image_thumb_addr")
    private String userImageThumbAddr;


}
