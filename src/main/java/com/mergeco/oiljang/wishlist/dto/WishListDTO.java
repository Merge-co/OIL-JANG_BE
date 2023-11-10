package com.mergeco.oiljang.wishlist.dto;

import java.util.UUID;

public class WishListDTO {
    private int wishCode;
    private int refProductCode;
    private int refUserCode;


    public WishListDTO() {
    }

    public WishListDTO(int wishCode, int refProductCode, int refUserCode) {
        this.wishCode = wishCode;
        this.refProductCode = refProductCode;
        this.refUserCode = refUserCode;
    }

    public int getWishCode() {
        return wishCode;
    }

    public void setWishCode(int wishCode) {
        this.wishCode = wishCode;
    }

    public int getRefProductCode() {
        return refProductCode;
    }

    public void setRefProductCode(int refProductCode) {
        this.refProductCode = refProductCode;
    }

    public int getRefUserCode() {
        return refUserCode;
    }

    public void setRefUserCode(int refUserCode) {
        this.refUserCode = refUserCode;
    }

    @Override
    public String toString() {
        return "WishListDTO{" +
                "wishCode=" + wishCode +
                ", refProductCode=" + refProductCode +
                ", refUserCode=" + refUserCode +
                '}';
    }
}
