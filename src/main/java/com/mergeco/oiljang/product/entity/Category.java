package com.mergeco.oiljang.product.entity;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@Getter
@ToString
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

}
