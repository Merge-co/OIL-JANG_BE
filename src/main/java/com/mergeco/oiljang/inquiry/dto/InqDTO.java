package com.mergeco.oiljang.inquiry.dto;

import com.mergeco.oiljang.inquiry.entity.InqCategory;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class InqDTO {
    private int inqCode;
    private String inqTitle;
    private String inqContent;
    private String inqAnswer;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate inqTime;
    private int refUserCode;
    private int inqCateCode;
    private String inqCateName;
    private String inqStatus;
   // private int userCode;


}
