package com.mergeco.oiljang.report.model.dto;

import com.mergeco.oiljang.product.dto.ProductDTO;
import com.mergeco.oiljang.product.dto.SellStatusDTO;
import lombok.*;

import java.time.LocalDateTime;

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
    private String productName; // 상품 코드 -> 판매글
    private String sellStateus; // 판매상태코드 -> 판매중인지 예약중인지.
    private int reportCategoryNo; // 신고분류 코드 FK -> 광고성 , 사기의심
    private String processDistincation; // 처리분류 => null
    private String processComment; // 처리 내용  null
    private LocalDateTime processDate; // 처리 일시 null

    public ReportDTO(String reportUserNick, String reportComment, LocalDateTime reportDate, String productName, String sellStateus, int reportCategoryNo) {
        this.reportUserNick = reportUserNick;
        this.reportComment = reportComment;
        this.reportDate = reportDate;
        this.productName = productName;
        this.sellStateus = sellStateus;
        this.reportCategoryNo = reportCategoryNo;
    }
}
