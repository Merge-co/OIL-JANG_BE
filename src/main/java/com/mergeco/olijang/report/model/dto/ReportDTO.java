package com.mergeco.olijang.report.model.dto;

import javax.persistence.Column;
import javax.persistence.Id;
import java.time.LocalDateTime;

public class ReportDTO {

    private int reportNo; // 신고 번호
    private String reportUserNick; // 신고자
    private String reportComment; // 신고 내용
    private LocalDateTime reportDate; //신고일시
    private int productCode; // 상품 코드
    private int sellStateusCode; // 판매상태코드
    private int refReportCategoryNo; // 신고분류 코드 FK
    private String processDistincation; // 처리분류
    private String processComment; // 처리 내용
    private LocalDateTime processDate; // 처리 일시

    public ReportDTO() {
    }

    public ReportDTO(int reportNo, String reportUserNick, String reportComment, LocalDateTime reportDate, int productCode, int sellStateusCode, int refReportCategoryNo, String processDistincation, String processComment, LocalDateTime processDate) {
        this.reportNo = reportNo;
        this.reportUserNick = reportUserNick;
        this.reportComment = reportComment;
        this.reportDate = reportDate;
        this.productCode = productCode;
        this.sellStateusCode = sellStateusCode;
        this.refReportCategoryNo = refReportCategoryNo;
        this.processDistincation = processDistincation;
        this.processComment = processComment;
        this.processDate = processDate;
    }

    public int getReportNo() {

        return reportNo;
    }

    public void setReportNo(int reportNo) {
        this.reportNo = reportNo;
    }

    public String getReportUserNick() {
        return reportUserNick;
    }

    public void setReportUserNick(String reportUserNick) {
        this.reportUserNick = reportUserNick;
    }

    public String getReportComment() {
        return reportComment;
    }

    public void setReportComment(String reportComment) {
        this.reportComment = reportComment;
    }

    public LocalDateTime getReportDate() {
        return reportDate;
    }

    public void setReportDate(LocalDateTime reportDate) {
        this.reportDate = reportDate;
    }

    public int getProductCode() {
        return productCode;
    }

    public void setProductCode(int productCode) {
        this.productCode = productCode;
    }

    public int getSellStateusCode() {
        return sellStateusCode;
    }

    public void setSellStateusCode(int sellStateusCode) {
        this.sellStateusCode = sellStateusCode;
    }

    public int getRefReportCategoryNo() {
        return refReportCategoryNo;
    }

    public void setRefReportCategoryNo(int refReportCategoryNo) {
        this.refReportCategoryNo = refReportCategoryNo;
    }

    public String getProcessDistincation() {
        return processDistincation;
    }

    public void setProcessDistincation(String processDistincation) {
        this.processDistincation = processDistincation;
    }

    public String getProcessComment() {
        return processComment;
    }

    public void setProcessComment(String processComment) {
        this.processComment = processComment;
    }

    public LocalDateTime getProcessDate() {
        return processDate;
    }

    public void setProcessDate(LocalDateTime processDate) {
        this.processDate = processDate;
    }

    @Override
    public String toString() {
        return "ReportDTO{" +
                "reportNo=" + reportNo +
                ", reportUserNick='" + reportUserNick + '\'' +
                ", reportComment='" + reportComment + '\'' +
                ", reportDate=" + reportDate +
                ", productCode=" + productCode +
                ", sellStateusCode=" + sellStateusCode +
                ", refReportCategoryNo=" + refReportCategoryNo +
                ", processDistincation='" + processDistincation + '\'' +
                ", processComment='" + processComment + '\'' +
                ", processDate=" + processDate +
                '}';
    }
}
