package com.mergeco.oiljang.product.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "Product")
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

    @Column(name = "wish_place_to_trade")
    private String wishPlaceTrade;

    @Column(name = "enroll_datetime")
    private LocalDateTime enrollDateTime;

    @Column(name = "view_count")
    private int viewCount;

    @Column(columnDefinition = "BINARY(16)", name = "ref_user_code")
    private UUID refUserCode;

    @ManyToOne
    @JoinColumn(name = "sell_status_code")
    private SellStatus SellStatus;

    @ManyToOne
    @JoinColumn(name = "ref_category_code")
    private Category Category;

    public Product productCode(int val) {
        productCode = val;
        return this;
    }

    public Product productName(String val) {
        productName = val;
        return this;
    }

    public Product productPrice(int val) {
        productPrice = val;
        return this;
    }

    public Product productDesc(String val) {
        productDesc = val;
        return this;
    }

    public Product wishPlaceTrade(String val) {
        wishPlaceTrade = val;
        return this;
    }

    public Product enrollDateTime(LocalDateTime val) {
        enrollDateTime = val;
        return this;
    }

    public Product viewCount(int val) {
        viewCount = val;
        return this;
    }

    public Product refUserCode(UUID val) {
        refUserCode = val;
        return this;
    }

    public Product category(Category val) {
        Category = val;
        return this;
    }

    public Product sellStatus(SellStatus val) {
        SellStatus = val;
        return this;
    }

    public Product builder() {
        return new Product(productCode, productName, productPrice, productDesc, wishPlaceTrade, enrollDateTime, viewCount, refUserCode, SellStatus, Category);
    }

    protected Product() {
    }

    public Product(int productCode, String productName, int productPrice, String productDesc, String wishPlaceTrade, LocalDateTime enrollDateTime, int viewCount, UUID refUserCode, SellStatus sellStatus, Category category) {
        this.productCode = productCode;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDesc = productDesc;
        this.wishPlaceTrade = wishPlaceTrade;
        this.enrollDateTime = enrollDateTime;
        this.viewCount = viewCount;
        this.refUserCode = refUserCode;
        SellStatus = sellStatus;
        Category = category;
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
                ", SellStatus=" + SellStatus +
                ", Category=" + Category +
                '}';
    }
}
