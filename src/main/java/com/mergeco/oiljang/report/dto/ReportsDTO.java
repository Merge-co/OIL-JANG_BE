package com.mergeco.oiljang.report.dto;


import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReportsDTO {

    private int reportNo; // 신고 번호 1
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime reportDate; //신고일시 2
    private String reportCategoryCode; // 신고분류 3
    private String productName; // 판매 게시글 4
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime processDate; // 처리 일시 5
    private String sellStatus; // 신고처리 결과 6
    private String reportComment; // 신고 사유 7
    private String processComment; // 신고처리 내용 8
    private String processDistinction; // 처리 결과분류 9
    private String reportUserNick; // 신고자 10
    private int reportUserCode;
    private String nickName; // 판매자 11
}
