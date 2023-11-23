package com.mergeco.oiljang.product.service;

import com.mergeco.oiljang.product.dto.*;
import com.mergeco.oiljang.product.entity.ProImageInfo;
import com.mergeco.oiljang.product.entity.Product;
import com.mergeco.oiljang.product.repository.ProImageRepository;
import com.mergeco.oiljang.product.repository.ProductRepository;
import com.mergeco.oiljang.wishlist.dto.WishListDTO;
import com.mergeco.oiljang.wishlist.repository.WishListRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductServiceTests {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProImageRepository proImageRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private WishListRepository wishListRepository;

    @Test
    void printService() {

        //then
        Assertions.assertNotNull(productService);
    }

    @Test
    void selectCategory() {

        //when
        List<CategoryDTO> productCategoryList = productService.findAllCategory();

        //then
        Assertions.assertNotNull(productCategoryList);
    }

//    @Test
//    void countProductList() {
//
//        //when
//        //then
//        Assertions.assertDoesNotThrow(
//                () -> productService.countProductList(1, -1, -1)
//        );
//    }

    @Test
    void selectProductList() {

        //when
        List<ProductListDTO> productListDTO = productService.selectProductList(0, 9, 1, "latest", 0, 10000000, null);

        //then
        Assertions.assertTrue(productListDTO.size() >= 0);
    }


    @Test
    @Transactional
    void insertProduct() {

        //given
        Long beforeCount = productRepository.count();
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
        Long afterCount = productRepository.count();

        //then
        Assertions.assertTrue(beforeCount + 1 == afterCount);
    }

    @Test
    void selectProductDetail() {

        //when
        List<ProductDetailDTO> productDetailDTOS = productService.selectProductDetail(2);

        //then
        Assertions.assertTrue(productDetailDTOS.size() > 0);

    }

    @Test
    @Transactional
    void selectProductDetailImg() {

        //given
        for(int i = 0; i < 5; i++) {
            ProImageInfoDTO proImageInfoDTO = new ProImageInfoDTO(0 , 2, "테스트 원본 이름", "테스트 변경된 이름", "테스트 원본 경로");
            proImageRepository.save(modelMapper.map(proImageInfoDTO, ProImageInfo.class));
        }

        //when
        Map<String, String> selectedProductDetailImg = productService.selectProductDetailImg(2);

        //then
        Assertions.assertTrue(selectedProductDetailImg.size() == 5);
    }

    @Test
    @Transactional
    void insertWishList() {

        //given
        Long beforeCount = wishListRepository.count();
        WishListDTO wishListDTO = new WishListDTO();
        wishListDTO.setRefProductCode(2);
        wishListDTO.setRefUserCode(1);

        //when
        productService.insertWishList(wishListDTO);
        Long afterCount = wishListRepository.count();

        //then
        Assertions.assertTrue(beforeCount + 1 == afterCount);
    }

    @Test
    @Transactional
    void updateViewCount() {

        //given
        Optional<Product> product = productRepository.findById(2);
        int beforeCount = product.get().getViewCount();

        //when
        productService.updateViewCount(2);
        int afterCount = product.get().getViewCount();

        //then
        Assertions.assertTrue(beforeCount + 1 == afterCount);
    }

    /* 민범님 이거 필요 없지 않나요? */
    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }


}

