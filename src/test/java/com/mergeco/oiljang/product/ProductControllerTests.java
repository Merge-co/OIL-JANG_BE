package com.mergeco.oiljang.product;

import com.mergeco.oiljang.product.dto.CategoryDTO;
import com.mergeco.oiljang.product.dto.ProductDTO;
import com.mergeco.oiljang.product.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

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
        List<Object[]> productList = productService.selectProductList(0, 9, 1, "latest", 0, 10000000);
        for(Object[] a : productList) {
            for(Object b : a) {
                System.out.println(b);
            }
        }
    }

    @Test
    void insertProduct() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setRefCategoryCode(1);
        productDTO.setEnrollDateTime(LocalDateTime.now());
        productDTO.setProductDesc("1");
        productDTO.setSellStatusCode("1");
        productDTO.setProductName("1");
        productDTO.setProductPrice(111);
        productDTO.setRefUserCode(UUID.randomUUID());
        productDTO.setWishPlaceTrade("1");
        productDTO.setRefCategoryCode(1);
        System.out.println(productDTO);
        productService.addProduct(productDTO);
    }

    @Test
    void selectProductDetail() {
        List<Object[]> productDetail = productService.selectProductDetail(1);
        for(Object[] a : productDetail) {
            for(Object b: a) {
                System.out.println(b);
            }
        }
    }

}
