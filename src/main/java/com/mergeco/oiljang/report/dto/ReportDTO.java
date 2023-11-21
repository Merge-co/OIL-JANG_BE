package com.mergeco.oiljang.report.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mergeco.oiljang.product.entity.SellStatus;
import jdk.jfr.DataAmount;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReportDTO {

    private int reportNo; // 신고 번호
    private String reportUserNick; // 신고자
    private String reportComment; // 신고 내용
    private LocalDateTime reportDate; //신고일시
    private int productCode; // 상품 코드 -> 판매글
    private int sellStatusCode; // 판매상태코드 -> 판매중인지 예약중인지.
    private int refReportCategoryNo; // 신고분류 코드 FK -> 광고성 , 사기의심
    private String processDistinction; // 처리분류 => null
    private String processComment; // 처리 내용  null
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime processDate; // 처리 일시 null

}
