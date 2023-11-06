package com.mergeco.oiljang.report.entity;

import javax.persistence.*;

@Entity(name = "ReportCategory")
@Table(name = "report_category")
public class ReportCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_category_no") // 인조 식별자
    private int reportCategoryNo; // 신고분류코드
    @Column(name = "report_category_code") // 얘가 필요한 코드
    private String reportCategoryCode; // 신고분류

protected ReportCategory () {}

    public ReportCategory(int reportCategoryNo, String reportCategoryCode) {
        this.reportCategoryNo = reportCategoryNo;
        this.reportCategoryCode = reportCategoryCode;
    }

    public int getReportCategoryNo() {
        return reportCategoryNo;
    }

    public String getReportCategoryCode() {
        return reportCategoryCode;
    }

    @Override
    public String toString() {
        return "ReportCategory{" +
                "reportCategoryNo=" + reportCategoryNo +
                ", reportCategoryCode='" + reportCategoryCode + '\'' +
                '}';
    }
}
