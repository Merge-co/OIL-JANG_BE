package com.mergeco.olijang.product.entity;

import javax.persistence.*;

@Entity
@Table(name = "category_info")
public class Category {

    @Id
    @Column(name = "category_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int categoryCode;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "upper_category_code")
    private int upperCategoryCode;

    protected Category() {
    }

    public Category(int categoryCode, String categoryName, int upperCategoryCode) {
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

    public int getUpperCategoryCode() {
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
