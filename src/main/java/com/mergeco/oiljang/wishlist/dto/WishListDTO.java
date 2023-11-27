package com.mergeco.oiljang.wishlist.dto;

import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class WishListDTO {
    private int wishCode;
    private int refProductCode;
    private int refUserCode;



}
