package com.mergeco.oiljang.inquiry.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class InqSelectDetailDTO {

    private int inqCode;
    private int refUserCode;
    private String name;
    private String id;
    private String inqTitle;
    private String inqContent;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate inqTime;
    private String inqAnswer;
    private String inqStatus;
    private int inqCateCode;
    private String inqCateName;
}
