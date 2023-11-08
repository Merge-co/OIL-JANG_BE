package com.mergeco.oiljang.report.model.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ReportCategoryDTO {

    private int reportCategoryNo; // 신고분류코드
    private String reportCategoryCode; // 신고분류

    public ReportCategoryDTO(int reportCategoryNo) {
    }
}
