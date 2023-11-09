package com.mergeco.oiljang.product;

import com.mergeco.oiljang.product.controller.ProductController;
import com.mergeco.oiljang.product.dto.CategoryDTO;
import com.mergeco.oiljang.product.dto.ProductDTO;
import com.mergeco.oiljang.product.dto.ProductDetailDTO;
import com.mergeco.oiljang.product.dto.ProductListDTO;
import com.mergeco.oiljang.product.entity.Product;
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

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductTests {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductController productController;

    @Autowired
    private ProductRepository productRepository;

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
    @Transactional
    void insertProduct() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductThumbAddr("한성");
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
    void selectProductDetailImg() {
        Map<String, String> selectedProductDetailImg = productService.selectProductDetailImg(5);
        for(String key : selectedProductDetailImg.keySet()) {
            System.out.println(key + " : " + selectedProductDetailImg.get(key));
        }
    }

    @Test
    @Transactional
    void insertWishList() {
        UUID uuid = UUID.fromString("52a9f8eb-7009-455b-b089-a9d374b06241");
        WishListDTO wishListDTO = new WishListDTO();
        wishListDTO.setRefProductCode(5);
        wishListDTO.setRefUserCode(uuid);
        productService.insertWishList(wishListDTO);
    }

    @Test
    @Transactional
    void updateViewCount() {
        productService.updateViewCount(5);
    }


//    @Test
//    void updateTest() {
//        productService.updateTest();
//    }


    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testUploadFileWithValidFile() throws IOException {

        MockMultipartFile fakeFile = new MockMultipartFile("userUploadedFile", "test-file.txt", "text/plain", "Hello, HanSung~!".getBytes());

        ResponseEntity<String> responseEntity = productController.uploadFile(fakeFile);

        //응답 확인
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(responseEntity.getBody(), "File uploaded successfully!");
    }

    @Test
    public void testUploadFileWithEmptyFile() throws IOException {
        MockMultipartFile emptyFile = new MockMultipartFile("userUploadedFile", new byte[1000]);
        ResponseEntity<String> responseEntity = productController.uploadFile(emptyFile);
        System.out.println(responseEntity);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(responseEntity.getBody(), "File uploaded successfully!");
    }

    private static final String TEMP_IMAGE_DIR = "temp_images";
    }



