package com.mergeco.oiljang.product.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class ProductDetail {
    //    SELECT m.productCode, m.productName, m.productPrice, m.Category.categoryName, (SELECT c.categoryName FROM Category c WHERE c.categoryCode = m.Category.upperCategoryCode) as upperCategoryCode, m.enrollDateTime, m.viewCount, (SELECT Count(w.wishCode) FROM WishList w WHERE w.refProductCode = :productCode) as wishCount, m.refUserCode, m.productDesc, m.wishPlaceTrade FROM Product m WHERE m.productCode = :productCode;
    private int productCode;
    private String productName;
    private int productPrice;
    private String categoryName;
    private String upperCategoryName;
    private LocalDateTime enrollDateTime;
    private int viewCount;
    private Long wishCount;
    private UUID refUserCode;
    private String productDesc;
    private String wishPlaceTrade;

    public ProductDetail() {
    }

    public ProductDetail(int productCode, String productName, int productPrice, String categoryName, String upperCategoryName, LocalDateTime enrollDateTime, int viewCount, Long wishCount, UUID refUserCode, String productDesc, String wishPlaceTrade) {
        this.productCode = productCode;
        this.productName = productName;
        this.productPrice = productPrice;
        this.categoryName = categoryName;
        this.upperCategoryName = upperCategoryName;
        this.enrollDateTime = enrollDateTime;
        this.viewCount = viewCount;
        this.wishCount = wishCount;
        this.refUserCode = refUserCode;
        this.productDesc = productDesc;
        this.wishPlaceTrade = wishPlaceTrade;
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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getUpperCategoryName() {
        return upperCategoryName;
    }

    public void setUpperCategoryName(String upperCategoryName) {
        this.upperCategoryName = upperCategoryName;
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

    public Long getWishCount() {
        return wishCount;
    }

    public void setWishCount(Long wishCount) {
        this.wishCount = wishCount;
    }

    public UUID getRefUserCode() {
        return refUserCode;
    }

    public void setRefUserCode(UUID refUserCode) {
        this.refUserCode = refUserCode;
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

    @Override
    public String toString() {
        return "ProductDetail{" +
                "productCode=" + productCode +
                ", productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                ", categoryName='" + categoryName + '\'' +
                ", upperCategoryName='" + upperCategoryName + '\'' +
                ", enrollDateTime=" + enrollDateTime +
                ", viewCount=" + viewCount +
                ", wishCount=" + wishCount +
                ", refUserCode=" + refUserCode +
                ", productDesc='" + productDesc + '\'' +
                ", wishPlaceTrade='" + wishPlaceTrade + '\'' +
                '}';
    }
}
