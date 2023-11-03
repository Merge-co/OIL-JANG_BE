package com.mergeco.olijang.product.dto;

public class ProductCategoryDTO {
    private int categoryCode;
    private String categoryName;
    private Integer upperCategoryCode;

    public ProductCategoryDTO() {
    }

    public ProductCategoryDTO(int categoryCode, String categoryName, Integer upperCategoryCode) {
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

    public void setUpperCategoryCode(Integer upperCategoryCode) {
        this.upperCategoryCode = upperCategoryCode;
    }

    @Override
    public String toString() {
        return "ProductCategoryDTO{" +
                "categoryCode=" + categoryCode +
                ", categoryName='" + categoryName + '\'' +
                ", upperCategoryCode=" + upperCategoryCode +
                '}';
    }
}
