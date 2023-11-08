package com.mergeco.oiljang.product.entity;

import javax.persistence.*;

@Entity(name = "ProImageInfo")
@Table(name = "pro_image_info")
public class ProImageInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pro_image_code")
    private int proImageCode;

    @Column(name = "ref_product_code")
    private int refProductCode;

    @Column(name = "pro_image_origin_name")
    private String proImageOriginName;

    @Column(name = "pro_image_db_name")
    private String proImageDbName;


    public ProImageInfo proImageCode(int val) {
        proImageCode = val;
        return this;
    }

    public ProImageInfo refProductCode(int val) {
        refProductCode = val;
        return this;
    }

    public ProImageInfo proImageOriginName(String val) {
        proImageOriginName = val;
        return this;
    }

    public ProImageInfo proImageDbName(String val) {
        proImageDbName = val;
        return this;
    }

    protected ProImageInfo() {
    }


    public ProImageInfo(int proImageCode, int refProductCode, String proImageOriginName, String proImageDbName) {
        this.proImageCode = proImageCode;
        this.refProductCode = refProductCode;
        this.proImageOriginName = proImageOriginName;
        this.proImageDbName = proImageDbName;
    }

    public int getProImageCode() {
        return proImageCode;
    }

    public int getRefProductCode() {
        return refProductCode;
    }

    public String getProImageOriginName() {
        return proImageOriginName;
    }

    public String getProImageDbName() {
        return proImageDbName;
    }

    @Override
    public String toString() {
        return "ProImageInfo{" +
                "proImageCode=" + proImageCode +
                ", refProductCode=" + refProductCode +
                ", proImageOriginName='" + proImageOriginName + '\'' +
                ", proImageDbName='" + proImageDbName + '\'' +
                '}';
    }
}
