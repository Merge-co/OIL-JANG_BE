package com.mergeco.oiljang.product.dto;

public class CategoryDTO {
    private int categoryCode;
    private String categoryName;
    private Integer upperCategoryCode;

    public CategoryDTO() {
    }

    public CategoryDTO(int categoryCode, String categoryName, Integer upperCategoryCode) {
        this.categoryCode = categoryCode;
        this.categoryName = categoryName;
        this.upperCategoryCode = upperCategoryCode;
    }

    public int getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(int categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getUpperCategoryCode() {
        return upperCategoryCode;
    }

    public void setUpperCategoryCode(int upperCategoryCode) {
        this.upperCategoryCode = upperCategoryCode;
    }

    @Override
    public String toString() {
        return "CategoryDTO{" +
                "categoryCode=" + categoryCode +
                ", categoryName='" + categoryName + '\'' +
                ", upperCategoryCode=" + upperCategoryCode +
                '}';
    }
}
