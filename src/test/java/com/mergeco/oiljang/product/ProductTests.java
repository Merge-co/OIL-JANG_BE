package com.mergeco.oiljang.product;

import com.mergeco.oiljang.product.dto.CategoryDTO;
import com.mergeco.oiljang.product.dto.ProductDTO;
import com.mergeco.oiljang.product.dto.ProductDetailDTO;
import com.mergeco.oiljang.product.dto.ProductListDTO;
import com.mergeco.oiljang.product.service.ProductService;
import com.mergeco.oiljang.wishlist.dto.WishListDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@SpringBootTest
public class ProductTests {

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
    void countProductList() {
        System.out.println(productService.countProductList());
    }

    @Test
    void selectProductList() {
        List<ProductListDTO> productListDTO = productService.selectProductList(0, 9, 1, "latest", 0, 10000000);
        for(ProductListDTO product : productListDTO) {
            System.out.println(product);
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
        UUID uuid = UUID.fromString("52a9f8eb-7009-455b-b089-a9d374b06241");
        productDTO.setRefUserCode(uuid);
        productDTO.setWishPlaceTrade("1");
        productDTO.setRefCategoryCode(1);
        System.out.println(productDTO);
        productService.addProduct(productDTO);

    }

    @Test
    void selectProductDetail() {
        List<ProductDetailDTO> productDetailDTOS = productService.selectProductDetail(5);
        for(ProductDetailDTO productDetailDTO : productDetailDTOS) {
            System.out.println(productDetailDTO);
        }
    }

    @Test
    void insertWishList() {
        UUID uuid = UUID.fromString("52a9f8eb-7009-455b-b089-a9d374b06241");
        WishListDTO wishListDTO = new WishListDTO();
        wishListDTO.setRefProductCode(5);
        wishListDTO.setRefUserCode(uuid);
        productService.insertWishList(wishListDTO);
    }

    @Test
    void updateViewCount() {
        for(int i = 0; i < 10; i++) {
            productService.updateViewCount(5);
        }
    }

    @Test
    void updateTest() {
        productService.updateTest();
    }

}
