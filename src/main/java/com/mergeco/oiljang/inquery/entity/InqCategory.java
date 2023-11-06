package com.mergeco.oiljang.inquery.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "inqCategory")
@Table(name = "inq_category")
public class InqCategory {
    @Id
    @Column(name = "inq_cate_code")
    private int inqCateCode;

    @Column(name = "inq_cate_name")
    private String inqCateName;

    protected InqCategory() {
    }

    public int getInqCateCode() {
        return inqCateCode;
    }

    public String getInqCateName() {
        return inqCateName;
    }

    @Override
    public String toString() {
        return "InqCategory{" +
                "inqCateCode=" + inqCateCode +
                ", inqCateName='" + inqCateName + '\'' +
                '}';
    }
}
