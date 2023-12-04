package com.mergeco.oiljang.userSanctions.dto;


import com.mergeco.oiljang.user.entity.User;
import lombok.*;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class UserSanctionsDTO {

    private int sanctionsCode;// ㅅㅏ용자 제제 코드
    private LocalDateTime sanctionsDate; // 사용자 제제날짜
    private User refUserCode; // 사용자 코드

}
