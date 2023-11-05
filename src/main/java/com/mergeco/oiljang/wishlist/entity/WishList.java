package com.mergeco.oiljang.wishlist.entity;

import javax.persistence.*;
import java.util.UUID;

@Entity(name = "WishList")
@Table(name = "wish_list")
public class WishList {

    @Id
    @Column(name = "wish_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int wishCode;

    @Column(name = "ref_product_code")
    private int refProductCode;

    @Column(columnDefinition = "BINARY(16)", name = "ref_user_code")
    private UUID refUserCode;

    protected WishList() {
    }

    public WishList(int wishCode, int refProductCode, UUID refUserCode) {
        this.wishCode = wishCode;
        this.refProductCode = refProductCode;
        this.refUserCode = refUserCode;
    }

    public int getWishCode() {
        return wishCode;
    }

    public int getRefProductCode() {
        return refProductCode;
    }

    public UUID getRefUserCode() {
        return refUserCode;
    }

    @Override
    public String toString() {
        return "WishList{" +
                "wishCode=" + wishCode +
                ", refProductCode=" + refProductCode +
                ", refUserCode=" + refUserCode +
                '}';
    }
}
