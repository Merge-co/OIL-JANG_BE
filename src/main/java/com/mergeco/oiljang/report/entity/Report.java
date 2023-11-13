package com.mergeco.oiljang.report.entity;

import com.mergeco.oiljang.product.entity.Product;
import com.mergeco.oiljang.product.entity.SellStatus;
import com.mergeco.oiljang.report.controller.ReportController;
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

    public Report(int reportNo, String reportUserNick, String reportComment, LocalDateTime reportDate, String processDistinction, String processComment, LocalDateTime processDate, Product product, SellStatus sellStatus, ReportCategory reportCategory) {
        this.reportNo = reportNo;
        this.reportComment = reportComment;
        this.reportUserNick = reportUserNick;
        this.sellStatus = sellStatus;
        this.reportDate = reportDate;
        this.processDistinction = processDistinction;
        this.processComment = processComment;
        this.processDate = processDate;
        this.product = product;
        this.reportCategory = reportCategory;

    }


    public Report reportUserNick(String reportUserNick) {
        this.reportUserNick = reportUserNick;
        return this;
    }

    public Report reportComment(String reportComment) {
        this.reportComment = reportComment;
        return this;
    }

    public Report reportDate(LocalDateTime reportDate) {
        this.reportDate = reportDate;
        return this;
    }

    public Report processDistinction(String processDistinction) {
        this.processDistinction = processDistinction;
        return this;
    }

    public Report processComment(String processComment) {
        this.processComment = processComment ;
        return this;
    }

    public Report processDate(LocalDateTime processDate) {
        this.processDate = processDate ;
        return this;
    }

    public Report sellStatus(SellStatus sellStatus){
        this.sellStatus = sellStatus;
        return this;
    }

    protected Report() {
    }

    public Report build() {
        return new Report(reportNo, reportUserNick,  reportComment,  reportDate,  processDistinction,  processComment,  processDate,  product,  sellStatus,  reportCategory);
    }

}
