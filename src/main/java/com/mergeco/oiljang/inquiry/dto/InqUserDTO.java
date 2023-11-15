package com.mergeco.oiljang.inquiry.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class InqUserDTO {
    int userCode;
    String name;
    String id;
    private int inqCode;
    private String inqTitle;
    private String inqContent;
    private String inqAnswer;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate inqTime;
    private int refUserCode;
    private InqCategoryDTO InqCategoryInqCategoryDTO;
    private String inqStatus;
}
