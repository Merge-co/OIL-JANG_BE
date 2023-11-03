package com.mergeco.oiljang.product;

import com.mergeco.oiljang.product.dto.CategoryDTO;
import com.mergeco.oiljang.product.dto.ProductDTO;
import com.mergeco.oiljang.product.service.ProductService;
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
        List<CategoryDTO> productCategoryList = productService.findAllCategory();
        System.out.println(productCategoryList);
        Assertions.assertNotNull(productCategoryList);
    }

    @Test
    void selectProductList() {
        List<Object[]> productList = productService.selectProductList();
        for(Object[] a : productList) {
            for(Object b : a) {
                System.out.println(b);
            }
        }
    }

    @Test
    void insertProduct() {
        ProductDTO productDTO = new ProductDTO();
        System.out.println();
    }

}
