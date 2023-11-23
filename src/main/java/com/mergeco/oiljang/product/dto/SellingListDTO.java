package com.mergeco.oiljang.product.dto;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class SellingListDTO {

    private int productCode;
    private String productThumbAddr;
    private String productName;
    private int productPrice;
    private Long wishCount;
    private String sellStatus;

    public SellingListDTO(int productCode, String productThumbAddr, String productName, int productPrice, Long wishCount, String sellStatusCode) {
        this.productCode = productCode;
        this.productThumbAddr = productThumbAddr;
        this.productName = productName;
        this.productPrice = productPrice;
        this.wishCount = wishCount;

        this.sellStatus = (sellStatusCode != null && sellStatusCode.equals("판매완료")) ? "판매완료" : "판매중";
    }

}
