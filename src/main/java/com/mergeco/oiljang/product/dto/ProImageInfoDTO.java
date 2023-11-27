package com.mergeco.oiljang.product.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ProImageInfoDTO {
    private int proImageCode;
    private int refProductCode;
    private String proImageOriginName;
    private String proImageDbName;
    private String proImageOriginAddr;

}