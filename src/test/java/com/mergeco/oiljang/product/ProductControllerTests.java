package com.mergeco.oiljang.product;

import com.mergeco.oiljang.product.dto.CategoryDTO;
import com.mergeco.oiljang.product.dto.ProductDTO;
import com.mergeco.oiljang.product.entity.Product;
import com.mergeco.oiljang.product.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
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
        List<Object[]> productList = productService.selectProductList();
        for(Object[] a : productList) {
            for(Object b : a) {
                System.out.println(b);
            }
        }
    }
    public ModelMapper modelMapper;

    private Product convertToEntity(ProductDTO productDTO) {
        return modelMapper.map(productDTO, Product.class);
    }

    @Test
    void insertProduct() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setRefCategoryCode(1);
        productDTO.setEnrollDateTime(LocalDateTime.now());
        productDTO.setProductDesc("1");
        productDTO.setProductSellStatus("1");
        productDTO.setProductName("1");
        productDTO.setProductPrice(111);
        productDTO.setRefUserCode(UUID.randomUUID());
        productDTO.setWishPlaceTrade("1");
        productDTO.setRefUserCode(UUID.randomUUID());
        productDTO.setRefCategoryCode(1);
        System.out.println(productDTO);
        productService.addProduct(productDTO);
    }

}
