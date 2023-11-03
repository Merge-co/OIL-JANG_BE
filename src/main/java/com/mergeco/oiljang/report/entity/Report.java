package com.mergeco.oiljang.report.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "Report")
@Table(name = "report")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_no")
    private int reportNo; // 신고 번호
    @Column(name = "report_user_nick")
    private String reportUserNick; // 신고자
    @Column(name = "report_comment")
    private String reportComment; // 신고 내용
    @Column(name = "report_date")
    private LocalDateTime reportDate; //신고일시
    @Column(name = "product_code")
    private int productCode; // 상품 코드
    @Column(name = "sell_state_code")
    private int sellStateusCode; // 판매상태코드
    @Column(name = "process_distincation")
    private String processDistincation; // 처리분류
    @Column(name = "process_comment")
    private String processComment; // 처리 내용
    @Column(name = "process_date")
    private LocalDateTime processDate; // 처리 일시
    @Column(name = "ref_report_category_no")
    private int refReportCategoryNo; // 신고분류 코드 FK


    public Report reportNo(int val) {
        reportNo = val;
        return this;
    }
    public Report reportUserNick(String val){
        reportUserNick = val;
        return this;
    }
    public Report reportComment(String val) {
        reportComment = val;
        return this;
    }
    public Report reportDate(LocalDateTime val) {
        reportDate = val;
        return this;
    }
    public Report productCode(int val) {
        productCode = val ;
        return this;
    }
    public Report sellStateusCode(int val) {
        sellStateusCode = val;
        return this;
    }
    public Report processComment(String val){
        processComment = val;
        return this;
    }
    public Report processDate (LocalDateTime val){
        processDate = val;
        return this;
    }
    public Report refReportCategoryNo (int val) {
        refReportCategoryNo = val;
        return this;
    }
    protected Report() {
    }

    public Report(int reportNo, String reportUserNick, String reportComment, LocalDateTime reportDate, int productCode, int sellStateusCode, int refReportCategoryNo, String processDistincation, String processComment, LocalDateTime processDate) {
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

    public String getReportUserNick() {
        return reportUserNick;
    }

    public String getReportComment() {
        return reportComment;
    }

    public LocalDateTime getReportDate() {
        return reportDate;
    }

    public int getProductCode() {
        return productCode;
    }

    public int getSellStateusCode() {
        return sellStateusCode;
    }

    public int getRefReportCategoryNo() {
        return refReportCategoryNo;
    }

    public String getProcessDistincation() {
        return processDistincation;
    }

    public String getProcessComment() {
        return processComment;
    }

    public LocalDateTime getProcessDate() {
        return processDate;
    }

    @Override
    public String toString() {
        return "Report{" +
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
