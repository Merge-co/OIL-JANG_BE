package com.mergeco.oiljang.inquery.dto;

import com.mergeco.oiljang.inquery.entity.InqCategory;
import com.mergeco.oiljang.user.model.dto.UserDTO;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class InqDTO {
    private int inqCode;

    private String inqTitle;

    private String inqContent;

    private String inqAnswer;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate inqTime;
    private String inqStatus;
    private List<UserDTO> userDTO;
    private InqCategory inqCategory;

    public InqDTO() {
    }

    public InqDTO(int inqCode, String inqTitle, String inqContent, String inqAnswer, LocalDate inqTime, String inqStatus, List<UserDTO> userDTO, InqCategory inqCategory) {
        this.inqCode = inqCode;
        this.inqTitle = inqTitle;
        this.inqContent = inqContent;
        this.inqAnswer = inqAnswer;
        this.inqTime = inqTime;
        this.inqStatus = inqStatus;
        this.userDTO = userDTO;
        this.inqCategory = inqCategory;
    }

    public int getInqCode() {
        return inqCode;
    }

    public void setInqCode(int inqCode) {
        this.inqCode = inqCode;
    }

    public String getInqTitle() {
        return inqTitle;
    }

    public void setInqTitle(String inqTitle) {
        this.inqTitle = inqTitle;
    }

    public String getInqContent() {
        return inqContent;
    }

    public void setInqContent(String inqContent) {
        this.inqContent = inqContent;
    }

    public String getInqAnswer() {
        return inqAnswer;
    }

    public void setInqAnswer(String inqAnswer) {
        this.inqAnswer = inqAnswer;
    }

    public LocalDate getInqTime() {
        return inqTime;
    }

    public void setInqTime(LocalDate inqTime) {
        this.inqTime = inqTime;
    }

    public String getInqStatus() {
        return inqStatus;
    }

    public void setInqStatus(String inqStatus) {
        this.inqStatus = inqStatus;
    }

    public List<UserDTO> getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(List<UserDTO> userDTO) {
        this.userDTO = userDTO;
    }

    public InqCategory getInqCategory() {
        return inqCategory;
    }

    public void setInqCategory(InqCategory inqCategory) {
        this.inqCategory = inqCategory;
    }

    @Override
    public String toString() {
        return "InqDTO{" +
                "inqCode=" + inqCode +
                ", inqTitle='" + inqTitle + '\'' +
                ", inqContent='" + inqContent + '\'' +
                ", inqAnswer='" + inqAnswer + '\'' +
                ", inqTime=" + inqTime +
                ", inqStatus='" + inqStatus + '\'' +
                ", userDTO=" + userDTO +
                ", inqCategory=" + inqCategory +
                '}';
    }
}
