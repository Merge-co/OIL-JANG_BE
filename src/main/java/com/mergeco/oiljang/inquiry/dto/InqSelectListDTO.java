package com.mergeco.oiljang.inquiry.dto;

import com.mergeco.oiljang.common.UserRole;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class InqSelectListDTO {
    private int inqCode;
    private int refUserCode;
    private int userCode;
    private String name;
    private String id;
    private UserRole role;
    private String inqTitle;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate inqTime;
    private String inqStatus;
    private int inqCateCode;
    private String inqCateName;
}
