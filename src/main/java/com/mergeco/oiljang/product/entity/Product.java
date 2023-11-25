package com.mergeco.oiljang.product.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Getter
@ToString
@Entity(name = "Product")
@Table(name = "product_info")
public class Product {

    @Id
    @Column(name = "product_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productCode;

    @Column(name = "product_thumb_addr")
    private String productThumbAddr;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_price")
    private int productPrice;

    @Column(name = "product_desc")
    private String productDesc;

    @Column(name = "wish_place_to_trade")
    private String wishPlaceTrade;

    @Column(name = "enroll_datetime")
    @CreationTimestamp
    private LocalDateTime enrollDateTime;

    @Column(name = "view_count")
    private int viewCount;

    @Column(name = "ref_user_code")
    private int refUserCode;

    @ManyToOne
    @ColumnDefault("1")
    @JoinColumn(name = "sell_status_code")
    private SellStatus SellStatus;

    @ManyToOne
    @JoinColumn(name = "ref_category_code")
    private Category Category;

    public Product productCode(int val) {
        productCode = val;
        return this;
    }

    public Product productThumbAddr(String val) {
        productThumbAddr = val;
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

    public Product refUserCode(int val) {
        refUserCode = val;
        return this;
    }

    public Product category(int val) {
        Category = new Category(val, null, 0);
        return this;
    }

    public Product sellStatus(int val) {
        SellStatus= new SellStatus(val, null);
        return this;
    }

    public Product builder() {
        return new Product(productCode, productThumbAddr, productName, productPrice, productDesc, wishPlaceTrade, enrollDateTime, viewCount, refUserCode, SellStatus, Category);
    }

    protected Product() {
    }

}
