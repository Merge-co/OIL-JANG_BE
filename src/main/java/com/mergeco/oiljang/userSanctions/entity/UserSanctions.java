package com.mergeco.oiljang.userSanctions.entity;


import com.mergeco.oiljang.user.entity.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@ToString
@Entity
@Table(name = "user_sanctions")
public class UserSanctions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sanctions_code")
    private int sanctionsCode;// ㅅㅏ용자 제제 코드
    @Column(name = "sanctions_date")
    private LocalDateTime sanctionsDate; // 사용자 제제날짜

    @JoinColumn(name = "ref_user_code")
    @ManyToOne
    private User refUserCode; // 사용자 코드

    protected UserSanctions(){}
}
