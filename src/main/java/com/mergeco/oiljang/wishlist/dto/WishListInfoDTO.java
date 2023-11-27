package com.mergeco.oiljang.wishlist.dto;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class WishListInfoDTO {
    private int wishCode;
    private String proImageThumbAddr;
    private String sellStatus;
    private String productName;
    private int productPrice;
    private String productDesc;
    private int productCode;

}
