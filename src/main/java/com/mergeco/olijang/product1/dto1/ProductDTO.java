package com.mergeco.olijang.product1.dto1;

import java.time.LocalDateTime;
import java.util.UUID;

public class ProductDTO {
    private int productCode;
    private String productName;
    private int productPrice;
    private String productDesc;
    private String wishPlaceTrade;
    private LocalDateTime enrollDateTime;
    private int viewCount;
    private UUID refUserCode;
    private int categoryCode;
    private String productSellStatus;

    public ProductDTO() {
    }

    public ProductDTO(int productCode, String productName, int productPrice, String productDesc, String wishPlaceTrade, LocalDateTime enrollDateTime, int viewCount, UUID refUserCode, int categoryCode, String productSellStatus) {
        this.productCode = productCode;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDesc = productDesc;
        this.wishPlaceTrade = wishPlaceTrade;
        this.enrollDateTime = enrollDateTime;
        this.viewCount = viewCount;
        this.refUserCode = refUserCode;
        this.categoryCode = categoryCode;
        this.productSellStatus = productSellStatus;
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

    public int getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(int categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getProductSellStatus() {
        return productSellStatus;
    }

    public void setProductSellStatus(String productSellStatus) {
        this.productSellStatus = productSellStatus;
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
                ", categoryCode=" + categoryCode +
                ", productSellStatus='" + productSellStatus + '\'' +
                '}';
    }
}
