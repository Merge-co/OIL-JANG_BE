package com.mergeco.oiljang.product.controller;

import com.mergeco.oiljang.common.restApi.ResponseMessage;
import com.mergeco.oiljang.product.dto.CategoryDTO;
import com.mergeco.oiljang.product.dto.ProductDTO;
import com.mergeco.oiljang.product.dto.ProductDetailDTO;
import com.mergeco.oiljang.product.dto.ProductListDTO;
import com.mergeco.oiljang.product.repository.ProductRepository;
import com.mergeco.oiljang.product.service.ProductService;
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
public class ProductControllerTests {

    @Autowired
    private ProductController productController;

    @Test
    void printProductController() {
        Assertions.assertNotNull(productController);
    }

    @Test
    void controllerSelectCategory() {

        //when
        ResponseEntity<ResponseMessage> result = productController.selectCategoryList();

        //then
        Assertions.assertEquals(result.getBody().getHttpStatusCode(), 200);
        Assertions.assertEquals(result.getBody().getMessage(), "카테고리 정보 조회 성공");
        Assertions.assertTrue(result.getBody().getResults().size() > 0);
    }

    @Test
    void controllerProductList() {

        //when
        ResponseEntity<ResponseMessage> result = productController.selectProductList(1, "list", 1, "latest", -1, -1);

        //then
        Assertions.assertEquals(result.getStatusCodeValue(), 200);
        Assertions.assertEquals(result.getBody().getMessage(), "중고 상품 목록 조회 성공");
        Assertions.assertTrue(result.getBody().getResults().size() > 0);
    }

    @Test
    void controllerProductDetail() {

        //when
        ResponseEntity<ResponseMessage> result = productController.selectProductInfo(2, null, null);

        //then
        Assertions.assertEquals(result.getStatusCodeValue(), 200);
        Assertions.assertEquals(result.getBody().getMessage(), "중고 상품 상세 조회 성공");
        Assertions.assertTrue(result.getBody().getResults().size() > 0);
    }

    @Test
    @Transactional
    void controllerRegistWishList() {

        //given
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductCode(2);

        //when
        ResponseEntity<ResponseMessage> result = productController.registWishlist(2, productDTO);

        //then
        Assertions.assertEquals(result.getStatusCodeValue(), 200);
        Assertions.assertEquals(result.getBody().getMessage(), "관심 목록 등록 성공");
        Assertions.assertTrue(result.getBody().getResults().size() > 0);
    }



}

