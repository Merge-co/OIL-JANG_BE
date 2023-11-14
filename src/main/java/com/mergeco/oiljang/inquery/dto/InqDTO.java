package com.mergeco.oiljang.inquery.dto;

import com.mergeco.oiljang.inquery.entity.InqCategory;
import com.mergeco.oiljang.user.model.dto.UserDTO;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

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
    private InqCategory inqCategory;
    private String inqStatus;
}
