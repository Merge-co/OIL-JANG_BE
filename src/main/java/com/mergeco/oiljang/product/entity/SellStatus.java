package com.mergeco.oiljang.product.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;

@AllArgsConstructor
@Getter
@ToString
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
}
