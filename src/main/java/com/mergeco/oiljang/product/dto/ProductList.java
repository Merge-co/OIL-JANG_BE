package com.mergeco.oiljang.product.dto;

import java.time.LocalDateTime;

public class ProductList {
    private int productCode;
    private String proImageThumbAddr;
    private String productName;
    private int productPrice;
    private LocalDateTime enrollDateTime;

    public ProductList() {
    }

    public ProductList(int productCode, String proImageThumbAddr, String productName, int productPrice, LocalDateTime enrollDateTime) {
        this.productCode = productCode;
        this.proImageThumbAddr = proImageThumbAddr;
        this.productName = productName;
        this.productPrice = productPrice;
        this.enrollDateTime = enrollDateTime;
    }

    public int getProductCode() {
        return productCode;
    }

    public void setProductCode(int productCode) {
        this.productCode = productCode;
    }

    public String getProImageThumbAddr() {
        return proImageThumbAddr;
    }

    public void setProImageThumbAddr(String proImageThumbAddr) {
        this.proImageThumbAddr = proImageThumbAddr;
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

    @Override
    public String toString() {
        return "ProductList{" +
                "productCode=" + productCode +
                ", proImageThumbAddr='" + proImageThumbAddr + '\'' +
                ", productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                ", enrollDateTime=" + enrollDateTime +
                '}';
    }
}
