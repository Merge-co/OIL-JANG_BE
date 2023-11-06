package com.mergeco.oiljang.wishlist.dto;

public class WishListInfoDTO {
    private int wishCode;
    private String proImageThumbAddr;
    private String sellStatus;
    private String productName;
    private int productPrice;
    private String productDesc;

    public WishListInfoDTO() {
    }

    public WishListInfoDTO(int wishCode, String proImageThumbAddr, String sellStatus, String productName, int productPrice, String productDesc) {
        this.wishCode = wishCode;
        this.proImageThumbAddr = proImageThumbAddr;
        this.sellStatus = sellStatus;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDesc = productDesc;
    }

    public int getWishCode() {
        return wishCode;
    }

    public void setWishCode(int wishCode) {
        this.wishCode = wishCode;
    }

    public String getProImageThumbAddr() {
        return proImageThumbAddr;
    }

    public void setProImageThumbAddr(String proImageThumbAddr) {
        this.proImageThumbAddr = proImageThumbAddr;
    }

    public String getSellStatus() {
        return sellStatus;
    }

    public void setSellStatus(String sellStatus) {
        this.sellStatus = sellStatus;
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

    @Override
    public String toString() {
        return "WishListInfoDTO{" +
                "wishCode=" + wishCode +
                ", proImageThumbAddr='" + proImageThumbAddr + '\'' +
                ", sellStatus='" + sellStatus + '\'' +
                ", productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                ", productDesc='" + productDesc + '\'' +
                '}';
    }
}
