package com.mergeco.oiljang.inquery.dto;

import java.time.LocalDate;
import java.util.Arrays;

public class InqDTO {
    private int inqCode;
    private String inqTitle;
    private String inqContent;
    private String inqAnswer;
    private LocalDate inqTime;
    private String inqStatus;
    private byte[] refUserCode;
    private int refInqCateCode;

    public InqDTO() {
    }

    public InqDTO(int inqCode, String inqTitle, String inqContent, String inqAnswer, LocalDate inqTime, String inqStatus, byte[] refUserCode, int refInqCateCode) {
        this.inqCode = inqCode;
        this.inqTitle = inqTitle;
        this.inqContent = inqContent;
        this.inqAnswer = inqAnswer;
        this.inqTime = inqTime;
        this.inqStatus = inqStatus;
        this.refUserCode = refUserCode;
        this.refInqCateCode = refInqCateCode;
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

    public byte[] getRefUserCode() {
        return refUserCode;
    }

    public void setRefUserCode(byte[] refUserCode) {
        this.refUserCode = refUserCode;
    }

    public int getRefInqCateCode() {
        return refInqCateCode;
    }

    public void setRefInqCateCode(int refInqCateCode) {
        this.refInqCateCode = refInqCateCode;
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
                ", refUserCode=" + Arrays.toString(refUserCode) +
                ", refInqCateCode=" + refInqCateCode +
                '}';
    }
}
