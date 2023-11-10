package com.mergeco.oiljang.wishlist.entity;

import com.mergeco.oiljang.product.entity.Product;

import javax.persistence.*;
import java.util.UUID;

@Entity(name = "WishList")
@Table(name = "wish_list")
public class WishList {

    @Id
    @Column(name = "wish_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int wishCode;

    @ManyToOne
    @JoinColumn(name = "ref_product_code")
    private Product product;

    @Column(name = "ref_user_code")
    private int refUserCode;

    protected WishList() {
    }

    public WishList(int wishCode, Product product, int refUserCode) {
        this.wishCode = wishCode;
        this.product = product;
        this.refUserCode = refUserCode;
    }

    public int getWishCode() {
        return wishCode;
    }

    public Product getProduct() {
        return product;
    }

    public int getRefUserCode() {
        return refUserCode;
    }

    @Override
    public String toString() {
        return "WishList{" +
                "wishCode=" + wishCode +
                ", product=" + product +
                ", refUserCode=" + refUserCode +
                '}';
    }
}
