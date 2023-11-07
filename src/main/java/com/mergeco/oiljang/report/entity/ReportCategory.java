package com.mergeco.oiljang.report.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;

@Entity(name = "ReportCategory")
@Table(name = "report_category")
@Getter
@ToString
@AllArgsConstructor
public class ReportCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_category_no") // 인조 식별자
    private int reportCategoryNo; // 신고분류코드
    @Column(name = "report_category_code") // 얘가 필요한 코드
    private String reportCategoryCode; // 신고분류

    protected ReportCategory() {
    }
}

