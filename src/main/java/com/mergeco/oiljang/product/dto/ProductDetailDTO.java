package com.mergeco.oiljang.product.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;


@AllArgsConstructor
@Getter
@Setter
@ToString
public class ProductDetailDTO {
    private int productCode;
    private String productName;
    private int productPrice;
    private String categoryName;
    private String upperCategoryName;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime enrollDateTime;
    private int viewCount;
    private Long wishCount;
    private int refUserCode;
    private String userImageThumbAddr;
    private String nickName;
    private String productDesc;
    private String wishPlaceTrade;
    private int sellStatusCode;

    public ProductDetailDTO() {
    }

}
