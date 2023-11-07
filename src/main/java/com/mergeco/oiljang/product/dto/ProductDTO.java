package com.mergeco.oiljang.product.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.UUID;

public class ProductDTO {
    private int productCode;
    private String productName;
    private int productPrice;
    private String productDesc;
    private String wishPlaceTrade;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime enrollDateTime;
    private int viewCount;
    private UUID refUserCode;
    private int refCategoryCode;
    private String sellStatusCode;

    public ProductDTO() {
    }

    public ProductDTO(int productCode, String productName, int productPrice, String productDesc, String wishPlaceTrade, LocalDateTime enrollDateTime, int viewCount, UUID refUserCode, int refCategoryCode, String sellStatusCode) {
        this.productCode = productCode;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDesc = productDesc;
        this.wishPlaceTrade = wishPlaceTrade;
        this.enrollDateTime = enrollDateTime;
        this.viewCount = viewCount;
        this.refUserCode = refUserCode;
        this.refCategoryCode = refCategoryCode;
        this.sellStatusCode = sellStatusCode;
    }

    public int getProductCode() {
        return productCode;
    }

    public void setProductCode(int productCode) {
        this.productCode = productCode;
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

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getWishPlaceTrade() {
        return wishPlaceTrade;
    }

    public void setWishPlaceTrade(String wishPlaceTrade) {
        this.wishPlaceTrade = wishPlaceTrade;
    }

    public LocalDateTime getEnrollDateTime() {
        return enrollDateTime;
    }

    public void setEnrollDateTime(LocalDateTime enrollDateTime) {
        this.enrollDateTime = enrollDateTime;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public UUID getRefUserCode() {
        return refUserCode;
    }

    public void setRefUserCode(UUID refUserCode) {
        this.refUserCode = refUserCode;
    }

    public int getRefCategoryCode() {
        return refCategoryCode;
    }

    public void setRefCategoryCode(int refCategoryCode) {
        this.refCategoryCode = refCategoryCode;
    }

    public String getSellStatusCode() {
        return sellStatusCode;
    }

    public void setSellStatusCode(String sellStatusCode) {
        this.sellStatusCode = sellStatusCode;
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
                "productCode=" + productCode +
                ", productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                ", productDesc='" + productDesc + '\'' +
                ", wishPlaceTrade='" + wishPlaceTrade + '\'' +
                ", enrollDateTime=" + enrollDateTime +
                ", viewCount=" + viewCount +
                ", refUserCode=" + refUserCode +
                ", refCategoryCode=" + refCategoryCode +
                ", productSellStatus='" + sellStatusCode + '\'' +
                '}';
    }
}
