package com.mergeco.oiljang.product.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.UUID;

public class ProductDetailDTO {
    private int productCode;
    private String productName;
    private int productPrice;
    private String categoryName;
    private String upperCategoryName;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime enrollDateTime;
    private int viewCount;
    private Long wishCount;
    private UUID refUserCode;
    private String userImageThumbAddr;
    private String nickName;
    private String productDesc;
    private String wishPlaceTrade;
    private String sellStatus;

    public ProductDetailDTO() {
    }

    public ProductDetailDTO(int productCode, String productName, int productPrice, String categoryName, String upperCategoryName, LocalDateTime enrollDateTime, int viewCount, Long wishCount, UUID refUserCode, String userImageThumbAddr, String nickName, String productDesc, String wishPlaceTrade, String sellStatus) {
        this.productCode = productCode;
        this.productName = productName;
        this.productPrice = productPrice;
        this.categoryName = categoryName;
        this.upperCategoryName = upperCategoryName;
        this.enrollDateTime = enrollDateTime;
        this.viewCount = viewCount;
        this.wishCount = wishCount;
        this.refUserCode = refUserCode;
        this.userImageThumbAddr = userImageThumbAddr;
        this.nickName = nickName;
        this.productDesc = productDesc;
        this.wishPlaceTrade = wishPlaceTrade;
        this.sellStatus = sellStatus;
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

    public String getUserImageThumbAddr() {
        return userImageThumbAddr;
    }

    public void setUserImageThumbAddr(String userImageThumbAddr) {
        this.userImageThumbAddr = userImageThumbAddr;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
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

    public String getSellStatus() {
        return sellStatus;
    }

    public void setSellStatus(String sellStatus) {
        this.sellStatus = sellStatus;
    }

    @Override
    public String toString() {
        return "ProductDetailDTO{" +
                "productCode=" + productCode +
                ", productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                ", categoryName='" + categoryName + '\'' +
                ", upperCategoryName='" + upperCategoryName + '\'' +
                ", enrollDateTime=" + enrollDateTime +
                ", viewCount=" + viewCount +
                ", wishCount=" + wishCount +
                ", refUserCode=" + refUserCode +
                ", userImageThumbAddr='" + userImageThumbAddr + '\'' +
                ", nickName='" + nickName + '\'' +
                ", productDesc='" + productDesc + '\'' +
                ", wishPlaceTrade='" + wishPlaceTrade + '\'' +
                ", sellStatus='" + sellStatus + '\'' +
                '}';
    }
}
