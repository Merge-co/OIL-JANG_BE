package com.mergeco.olijang.product.entity;

import javax.persistence.*;

@Entity
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

    @Column(name = "pro_image_thumb_addr")
    private String proImageThumbAddr;

    @Column(name = "pro_image_origin_addr")
    private String proImageOriginAddr;

    protected ProImageInfo() {
    }

    public ProImageInfo(int proImageCode, int refProductCode, String proImageOriginName, String proImageDbName, String proImageThumbAddr, String proImageOriginAddr) {
        this.proImageCode = proImageCode;
        this.refProductCode = refProductCode;
        this.proImageOriginName = proImageOriginName;
        this.proImageDbName = proImageDbName;
        this.proImageThumbAddr = proImageThumbAddr;
        this.proImageOriginAddr = proImageOriginAddr;
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

    public String getProImageThumbAddr() {
        return proImageThumbAddr;
    }

    public String getProImageOriginAddr() {
        return proImageOriginAddr;
    }

    @Override
    public String toString() {
        return "ProImageInfo{" +
                "proImageCode=" + proImageCode +
                ", refProductCode=" + refProductCode +
                ", proImageOriginName='" + proImageOriginName + '\'' +
                ", proImageDbName='" + proImageDbName + '\'' +
                ", proImageThumbAddr='" + proImageThumbAddr + '\'' +
                ", proImageOriginAddr='" + proImageOriginAddr + '\'' +
                '}';
    }
}
