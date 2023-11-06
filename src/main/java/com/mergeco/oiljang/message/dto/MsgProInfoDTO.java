package com.mergeco.oiljang.message.dto;

import com.mergeco.oiljang.product.dto.ProductDTO;
import lombok.*;

import java.time.LocalDate;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MsgProInfoDTO {
    private int msgCode;
    private int productCode;
    private String productName;
    private String productDesc;
}
