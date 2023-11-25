package com.mergeco.oiljang.product.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;

@AllArgsConstructor
@Getter
@ToString
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

    @Column(name = "pro_image_origin_addr")
    private String proImageOriginAddr;

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

    public ProImageInfo proImageOriginAddr(String val) {
        proImageOriginAddr = val;
        return this;
    }

    protected ProImageInfo() {
    }
}
