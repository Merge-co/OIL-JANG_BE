package com.mergeco.oiljang.report.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "ReportCategory")
@Table(name = "report_category")
@Getter
@ToString
@AllArgsConstructor
public class ReportCategory {

    @Id
    @Column(name = "report_category_no") // 인조 식별자
    private int reportCategoryNo; // 신고분류코드

    @Column(name = "report_category_code")
    private String reportCategoryCode; // 신고분류

    protected ReportCategory() {
    }
    public ReportCategory build() {
        return new ReportCategory(reportCategoryNo, reportCategoryCode);
    }

    public ReportCategory reportCategoryNo(int reportCategoryNo) {
        this.reportCategoryNo = reportCategoryNo;
        return this;
    }

}

