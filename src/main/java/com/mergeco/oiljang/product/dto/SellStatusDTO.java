package com.mergeco.oiljang.product.dto;

public class SellStatusDTO {
    private int sellStatusCode;
    private String sellStatus;

    public SellStatusDTO() {
    }

    public SellStatusDTO(int sellStatusCode, String sellStatus) {
        this.sellStatusCode = sellStatusCode;
        this.sellStatus = sellStatus;
    }

    public int getSellStatusCode() {
        return sellStatusCode;
    }

    public void setSellStatusCode(int sellStatusCode) {
        this.sellStatusCode = sellStatusCode;
    }

    public String getSellStatus() {
        return sellStatus;
    }

    public void setSellStatus(String sellStatus) {
        this.sellStatus = sellStatus;
    }

    @Override
    public String toString() {
        return "SellStatusDTO{" +
                "sellStatusCode=" + sellStatusCode +
                ", sellStatus='" + sellStatus + '\'' +
                '}';
    }
}
