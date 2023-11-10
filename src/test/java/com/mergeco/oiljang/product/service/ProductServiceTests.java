package com.mergeco.oiljang.product.service;

import com.mergeco.oiljang.common.restApi.ResponseMessage;
import com.mergeco.oiljang.product.controller.ProductController;
import com.mergeco.oiljang.product.dto.CategoryDTO;
import com.mergeco.oiljang.product.dto.ProductDTO;
import com.mergeco.oiljang.product.dto.ProductDetailDTO;
import com.mergeco.oiljang.product.dto.ProductListDTO;
import com.mergeco.oiljang.product.repository.ProductRepository;
import com.mergeco.oiljang.wishlist.dto.WishListDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductServiceTests {

    @Autowired
    private ProductService productService;

    @Test
    void printService() {
        Assertions.assertNotNull(productService);
    }

    @Test
    void selectCategory() {

        //when
        List<CategoryDTO> productCategoryList = productService.findAllCategory();

        //then
        Assertions.assertNotNull(productCategoryList);
    }

    @Test
    void countProductList() {

        //then
        Assertions.assertDoesNotThrow(
                () -> productService.countProductList()
        );
    }

    @Test
    void selectProductList() {

        //when
        List<ProductListDTO> productListDTO = productService.selectProductList(0, 9, 1, "latest", 0, 10000000);

        //then
        Assertions.assertTrue(productListDTO.size() >= 0 );
    }


    @Test
    @Transactional
    void insertProduct() {

        //given
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductThumbAddr("한성");
        productDTO.setRefCategoryCode(1);
        productDTO.setEnrollDateTime(LocalDateTime.now());
        productDTO.setProductDesc("1");
        productDTO.setSellStatusCode("1");
        productDTO.setProductName("1");
        productDTO.setProductPrice(111);
        productDTO.setRefUserCode(1);
        productDTO.setWishPlaceTrade("1");
        productDTO.setRefCategoryCode(1);

        //when
        productService.addProduct(productDTO);

        //then


    }

    @Test
    void selectProductDetail() {

        //when
        List<ProductDetailDTO> productDetailDTOS = productService.selectProductDetail(5);

        //then
        // Assertions.a

    }

    @Test
    void selectProductDetailImg() {
        Map<String, String> selectedProductDetailImg = productService.selectProductDetailImg(5);
        for(String key : selectedProductDetailImg.keySet()) {
            System.out.println(key + " : " + selectedProductDetailImg.get(key));
        }
    }

    @Test
    @Transactional
    void insertWishList() {
        WishListDTO wishListDTO = new WishListDTO();
        wishListDTO.setRefProductCode(5);
        wishListDTO.setRefUserCode(1);
        productService.insertWishList(wishListDTO);
    }

    @Test
    @Transactional
    void updateViewCount() {
        productService.updateViewCount(5);
    }

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }


}

