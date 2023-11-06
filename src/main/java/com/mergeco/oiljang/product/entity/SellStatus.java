package com.mergeco.oiljang.product.entity;

import javax.persistence.*;

@Entity(name = "SellStatus")
@Table(name = "sell_status_info")
public class SellStatus {

    @Id
    @Column(name = "sell_status_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sellStatusCode;

    @Column(name = "sell_status")
    private String sellStatus;

    public SellStatus sellStatusCode(int val) {
        sellStatusCode = val;
        return this;
    }

    public SellStatus sellStatus(String val) {
        sellStatus = val;
        return this;
    }
    protected SellStatus() {
    }

    public SellStatus(int sellStatusCode, String sellStatus) {
        this.sellStatusCode = sellStatusCode;
        this.sellStatus = sellStatus;
    }

    public int getSellStatusCode() {
        return sellStatusCode;
    }

    public String getSellStatus() {
        return sellStatus;
    }

    @Override
    public String toString() {
        return "SellStatus{" +
                "sellStatusCode=" + sellStatusCode +
                ", sellStatus='" + sellStatus + '\'' +
                '}';
    }
}
