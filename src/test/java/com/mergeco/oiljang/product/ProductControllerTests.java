package com.mergeco.oiljang.product;

import com.mergeco.oiljang.product.dto.CategoryDTO;
import com.mergeco.oiljang.product.dto.ProductDTO;
import com.mergeco.oiljang.product.service.ProductService;
import com.mergeco.oiljang.wishlist.dto.WishListDTO;
import com.mergeco.oiljang.wishlist.service.WishListService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootTest
public class ProductControllerTests {

    @Autowired
    private ProductService productService;

    @Autowired
    private WishListService wishListService;

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
        List<Integer> categoryCodes = new ArrayList<>();
        categoryCodes.add(1);
        categoryCodes.add(2);
        categoryCodes.add(3);
        categoryCodes.add(4);
        categoryCodes.add(5);
        List<Object[]> productList = productService.selectProductList(0, 9, categoryCodes, "latest", 0, 10000000);
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
        UUID uuid = UUID.fromString("52a9f8eb-7009-455b-b089-a9d374b06241");
        productDTO.setRefUserCode(uuid);
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

    @Test
    void insertWishList() {
        UUID uuid = UUID.fromString("52a9f8eb-7009-455b-b089-a9d374b06241");
        WishListDTO wishListDTO = new WishListDTO();
        wishListDTO.setRefProductCode(1);
        wishListDTO.setRefUserCode(uuid);
        wishListService.insertWishList(wishListDTO);
    }

}
