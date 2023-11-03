package com.mergeco.oiljang.product.entity;

import com.fasterxml.jackson.annotation.JsonTypeId;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "product_info")
public class Product {

    @Id
    @Column(name = "product_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productCode;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_price")
    private int productPrice;

    @Column(name = "product_desc")
    private String productDesc;

    @Column(name = "wish_place_trade")
    private String wishPlaceTrade;

    @Column(name = "enroll_datetime")
    private LocalDateTime enrollDateTime;

    @Column(name = "view_count")
    private int viewCount;

    @Column(name = "ref_user_code")
    private UUID refUserCode;

    @Column(name = "category_code")
    private int categoryCode;

    @Column(name = "product_sell_status")
    private String productSellStatus;

    protected Product() {
    }

    public Product(int productCode, String productName, int productPrice, String productDesc, String wishPlaceTrade, LocalDateTime enrollDateTime, int viewCount, UUID refUserCode, int categoryCode, String productSellStatus) {
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

    public String getProductName() {
        return productName;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public String getWishPlaceTrade() {
        return wishPlaceTrade;
    }

    public LocalDateTime getEnrollDateTime() {
        return enrollDateTime;
    }

    public int getViewCount() {
        return viewCount;
    }

    public UUID getRefUserCode() {
        return refUserCode;
    }

    public int getCategoryCode() {
        return categoryCode;
    }

    public String getProductSellStatus() {
        return productSellStatus;
    }

    @Override
    public String toString() {
        return "Product{" +
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
