package com.mergeco.oiljang.inquery.dto;

public class InqCategoryDTO {
    private int inqCateCode;
    private String inqCateName;

    public InqCategoryDTO() {
    }

    public InqCategoryDTO(int inqCateCode, String inqCateName) {
        this.inqCateCode = inqCateCode;
        this.inqCateName = inqCateName;
    }

    public int getInqCateCode() {
        return inqCateCode;
    }

    public void setInqCateCode(int inqCateCode) {
        this.inqCateCode = inqCateCode;
    }

    public String getInqCateName() {
        return inqCateName;
    }

    public void setInqCateName(String inqCateName) {
        this.inqCateName = inqCateName;
    }

    @Override
    public String toString() {
        return "InqCategoryDTO{" +
                "inqCateCode=" + inqCateCode +
                ", inqCateName='" + inqCateName + '\'' +
                '}';
    }
}
