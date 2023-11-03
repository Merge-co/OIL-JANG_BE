package com.mergeco.olijang.product;

import com.olijang.product.dto.ProductCategoryDTO;
import com.olijang.product.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ProductControllerTests {

    @Autowired
    private ProductService productService;

    @Test
    void printService() {
        Assertions.assertNotNull(productService);
    }

    @Test
    void selectCategory() {
        List<ProductCategoryDTO> productCategoryList = productService.findCategoryList();
        System.out.println(productCategoryList);
        Assertions.assertNotNull(productCategoryList);
    }

}