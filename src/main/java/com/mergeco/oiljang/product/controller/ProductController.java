package com.mergeco.oiljang.product.controller;

import com.mergeco.oiljang.product.dto.CategoryDTO;
import com.mergeco.oiljang.product.dto.ProductDTO;
import com.mergeco.oiljang.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

//    @GetMapping("/categories/")
//    public void selectCategory() {
//        HttpHeaders
//    }

    @GetMapping("/products")
    public void selectProductList() {

    }

    @GetMapping("/products/{productCode}")
    public void selectProductInfo(@PathVariable int productCode) {

    }

    public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductDTO productDTO) {
        ProductDTO addedProduct = productService.addProduct(productDTO);

        return new ResponseEntity<>(addedProduct, HttpStatus.CREATED);
    }

    @PostMapping("/products/{productCode}/wishLists")
    public void registWishlist(@PathVariable int productCode) {

    }

    @PostMapping("/products/{productCode}/reports")
    public void registReport(@PathVariable int productCode) {

    }

    @GetMapping(value = "/categories" , produces = "application/json; charset=UTF-8")
    @ResponseBody
    public List<CategoryDTO> findCategoryList() {
        return productService.findAllCategory();
    }

}
