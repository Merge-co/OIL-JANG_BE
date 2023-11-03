package com.mergeco.olijang.product1;

import com.mergeco.olijang.product1.dto1.ProductCategoryDTO;
import com.mergeco.olijang.product1.entity1.Product;
import com.mergeco.olijang.product1.service1.ProductService;
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

    @Test
    void selectProductList() {
        List<Product> productList = productService.selectProductList();
        System.out.println(productList);
    }

}
