package com.mergeco.oiljang.product.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SellingListDTO {

    private int productCode;
    private String productThumbAddr;
    private String productName;
    private int productPrice;
    private int wishCount;
    private String sellStatus;

}
