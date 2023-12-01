package com.mergeco.oiljang.report.dto;


import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProcessDetailDTO {

    private int reportNo; // 신고 번호
    private LocalDateTime reportDate; // 신고 일시
    private String reportCategoryCode; // 신고분류
    private int userCode; // 사용자번호
    private String productName;// 상품 이름
    private String reportComment ;// 신고내용
    private String nickName; // 닉네임
    private String id; // 아이디
    private Long count; // 삭제된게시글에 대한 사용자 삭제 횟수
}
