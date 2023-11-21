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

    private int reportNo; // 신고 번호
    private LocalDateTime reportDate; //신고일시; // 신고분류 코드 FK -> 광고성 , 사기의심
    private String reportCategoryCode; // 신고분류 코드 FK -> 광고성 , 사기의심
    private int productCode; // 상품 코드 -> 판매글
    private LocalDateTime processDate; // 처리 일시 null
    private int sellStatusCode; // 판매상태코드 -> 판매중인지 예약중인지.
    private String reportComment; // 신고 내용
    private String processComment; // 처리 내용  null
    private String reportUserNick; // 신고자
    private String nickName; // 판매자





}
