package com.mergeco.oiljang.product.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class ProductListDTO {
    private int productCode;
    private String proImageThumbAddr;
    private String productName;
    private int productPrice;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime enrollDateTime;
    private String sellStatus;

    public ProductListDTO() {
    }

    public ProductListDTO(int productCode, String proImageThumbAddr, String productName, int productPrice, LocalDateTime enrollDateTime, String sellStatus) {
        this.productCode = productCode;
        this.proImageThumbAddr = proImageThumbAddr;
        this.productName = productName;
        this.productPrice = productPrice;
        this.enrollDateTime = enrollDateTime;
        this.sellStatus = sellStatus;
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

    public String getSellStatus() {
        return sellStatus;
    }

    public void setSellStatus(String sellStatus) {
        this.sellStatus = sellStatus;
    }

    @Override
    public String toString() {
        return "ProductList{" +
                "productCode=" + productCode +
                ", proImageThumbAddr='" + proImageThumbAddr + '\'' +
                ", productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                ", enrollDateTime=" + enrollDateTime +
                ", sellStatus='" + sellStatus + '\'' +
                '}';
    }
}
