package com.mergeco.oiljang.report.entity;

import com.mergeco.oiljang.product.entity.Product;
import com.mergeco.oiljang.product.entity.SellStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "tbl_report")
@Table(name = "report")
@AllArgsConstructor
@ToString
@Getter

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

    @JoinColumn(name = "product_code")
    @ManyToOne
    private Product product; // 상품 코드

    @JoinColumn(name = "sell_status_code")
    @ManyToOne
    private SellStatus sellStatus; // 판매상태코드

    @Column(name = "process_distinction")
    private String processDistinction; // 처리분류
    @Column(name = "process_comment")
    private String processComment; // 처리 내용
    @Column(name = "process_date")
    private LocalDateTime processDate; // 처리 일시

    @JoinColumn(name = "ref_report_category_no")
    @ManyToOne
    private ReportCategory reportCategory;// 신고분류 코드 FK


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
    public Report processDistinction(String val){
        processDistinction = val;
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
    protected Report() {
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

    public String getProcessDistinction() {
        return processDistinction;
    }

    public String getProcessComment() {
        return processComment;
    }

    public LocalDateTime getProcessDate() {
        return processDate;
    }
}
