package com.mergeco.oiljang.userSanctions.dto;


import com.mergeco.oiljang.user.entity.User;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class UserSanctionsDTO {

    private int sanctionsCode;// ㅅㅏ용자 제제 코드
    private LocalDateTime sanctionsDate; // 사용자 제제날짜
    private int refUserCode; // 사용자 코드
    private LocalDateTime managerDate;// 관리자 등록한 날짜


}