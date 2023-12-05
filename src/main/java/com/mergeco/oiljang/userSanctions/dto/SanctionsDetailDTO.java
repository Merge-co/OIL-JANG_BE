package com.mergeco.oiljang.userSanctions.dto;

import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SanctionsDetailDTO {

    private int sanctionsCode;// ㅅㅏ용자 제제 코드
    private LocalDateTime sanctionsDate; // 사용자 제제날짜
    private int refUserCode; // 사용자 코드
    private String userId; //유저 아이디
    private String nickName; //유저 별명
    private long count ; // 신고 제제당한 횟수
}
