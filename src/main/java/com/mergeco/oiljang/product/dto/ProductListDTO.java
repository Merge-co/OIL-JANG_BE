package com.mergeco.oiljang.product.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class ProductListDTO {
    private int productCode;
    private String productThumbAddr;
    private String productName;
    private int productPrice;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime enrollDateTime;
    private String categoryName;
    private int refUserCode;

    public ProductListDTO() {
    }

    public ProductListDTO(int productCode, String productThumbAddr, String productName, int productPrice, LocalDateTime enrollDateTime, String categoryName, int refUserCode) {
        this.productCode = productCode;
        this.productThumbAddr = productThumbAddr;
        this.productName = productName;
        this.productPrice = productPrice;
        this.enrollDateTime = enrollDateTime;
        this.categoryName = categoryName;
        this.refUserCode = refUserCode;
    }

    public int getProductCode() {
        return productCode;
    }

    public void setProductCode(int productCode) {
        this.productCode = productCode;
    }

    public String getProductThumbAddr() {
        return productThumbAddr;
    }

    public void setProductThumbAddr(String productThumbAddr) {
        this.productThumbAddr = productThumbAddr;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public LocalDateTime getEnrollDateTime() {
        return enrollDateTime;
    }

    public void setEnrollDateTime(LocalDateTime enrollDateTime) {
        this.enrollDateTime = enrollDateTime;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getRefUserCode() {
        return refUserCode;
    }

    public void setRefUserCode(int refUserCode) {
        this.refUserCode = refUserCode;
    }

    @Override
    public String toString() {
        return "ProductListDTO{" +
                "productCode=" + productCode +
                ", productThumbAddr='" + productThumbAddr + '\'' +
                ", productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                ", enrollDateTime=" + enrollDateTime +
                ", categoryCode='" + categoryName + '\'' +
                ", refUserCode=" + refUserCode +
                '}';
    }
}
