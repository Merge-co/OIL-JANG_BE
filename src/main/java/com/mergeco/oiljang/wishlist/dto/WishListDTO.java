package com.mergeco.oiljang.wishlist.dto;

public class WishListDTO {
    private int wishCode;
    private int refUserCode;
    private int refProductCode;

    public WishListDTO() {
    }

    public WishListDTO(int wishCode, int refUserCode, int refProductCode) {
        this.wishCode = wishCode;
        this.refUserCode = refUserCode;
        this.refProductCode = refProductCode;
    }

    public int getWishCode() {
        return wishCode;
    }

    public void setWishCode(int wishCode) {
        this.wishCode = wishCode;
    }

    public int getRefUserCode() {
        return refUserCode;
    }

    public void setRefUserCode(int refUserCode) {
        this.refUserCode = refUserCode;
    }

    public int getRefProductCode() {
        return refProductCode;
    }

    public void setRefProductCode(int refProductCode) {
        this.refProductCode = refProductCode;
    }

    @Override
    public String toString() {
        return "WishListDTO{" +
                "wishCode=" + wishCode +
                ", refUserCode=" + refUserCode +
                ", refProductCode=" + refProductCode +
                '}';
    }
}
