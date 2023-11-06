package com.mergeco.oiljang.product.entity;

import javax.persistence.*;

@Entity(name = "Category")
@Table(name = "category_info")
public class Category {

    @Id
    @Column(name = "category_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int categoryCode;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "upper_category_code")
    private Integer upperCategoryCode;

    public Category categoryCode(int val) {
        categoryCode = val;
        return this;
    }

    public Category categoryName(String val) {
        categoryName = val;
        return this;
    }

    public Category upperCategoryCode(Integer val) {
        upperCategoryCode = val;
        return this;
    }

    protected Category() {
    }

    public Category(int categoryCode, String categoryName, Integer upperCategoryCode) {
        this.categoryCode = categoryCode;
        this.categoryName = categoryName;
        this.upperCategoryCode = upperCategoryCode;
    }

    public int getCategoryCode() {
        return categoryCode;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public Integer getUpperCategoryCode() {
        return upperCategoryCode;
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryCode=" + categoryCode +
                ", categoryName='" + categoryName + '\'' +
                ", upperCategoryCode=" + upperCategoryCode +
                '}';
    }
}
