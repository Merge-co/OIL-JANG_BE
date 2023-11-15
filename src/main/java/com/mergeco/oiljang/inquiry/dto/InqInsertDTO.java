package com.mergeco.oiljang.inquiry.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class InqInsertDTO {
    private int inqCode;
    private String inqTitle;
    private String inqContent;
    private String inqAnswer;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate inqTime;
    private int refUserCode;
    private InqCategoryDTO inqCategoryInqCategoryDTO;
    private String inqStatus;

    public InqInsertDTO(String inqTitle, String inqContent, String inqAnswer, LocalDate inqTime, int refUserCode, InqCategoryDTO inqCategoryDTO, String inqStatus) {
        this.inqTitle = inqTitle;
        this.inqContent = inqContent;
        this.inqAnswer = inqAnswer;
        this.inqTime = inqTime;
        this.refUserCode = refUserCode;
        this.inqCategoryInqCategoryDTO = inqCategoryDTO;
        this.inqStatus = inqStatus;
    }
}
