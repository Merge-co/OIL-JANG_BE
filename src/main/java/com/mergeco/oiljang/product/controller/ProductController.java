package com.mergeco.oiljang.product.controller;

import com.mergeco.oiljang.common.paging.JpqlPagingButton;
import com.mergeco.oiljang.common.restApi.ResponseMessage;
import com.mergeco.oiljang.product.dto.CategoryDTO;
import com.mergeco.oiljang.product.dto.ProductDTO;
import com.mergeco.oiljang.product.dto.ProductDetailDTO;
import com.mergeco.oiljang.product.dto.ProductListDTO;
import com.mergeco.oiljang.product.service.ProductService;
import com.mergeco.oiljang.wishlist.dto.WishListDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.*;


@Api(tags = "중고 상품 관련")
@RestController
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @ApiOperation(value = "중고 상품 카테고리 목록 조회")
    @GetMapping("/categories")
    public ResponseEntity<ResponseMessage> selectCategoryList() {

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        List<CategoryDTO> productCategoryList = productService.findAllCategory();

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("productCategoryList", productCategoryList);

        ResponseMessage responseMessage = new ResponseMessage(200, "카테고리 정보", responseMap);

        return new ResponseEntity<>(responseMessage, headers, HttpStatus.OK);
    }

    @ApiOperation(value = "중고 상품 목록 조회")
    @GetMapping("/products")
    public ResponseEntity<ResponseMessage> selectProductList(@RequestParam(required = false) Integer page, @RequestParam String pageKind, @RequestParam(required = false) Integer categoryCode, @RequestParam(required = false, defaultValue = "latest") String sortCondition, @RequestParam(required = false) Integer minPrice, @RequestParam(required = false) Integer maxPrice) {

        if(page == null) {
            page = 1;
        }

        if(categoryCode == null) {
            categoryCode = 1;
        }

        if(minPrice == null) {
            minPrice = -1;
        }

        if(maxPrice == null) {
            maxPrice = -1;
        }


        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        int limit = 0;
        switch (pageKind) {
            case "merge":
                limit = 6;
            case "list":
                limit = 8;
        }

        int offset = limit * (page - 1);
        List<ProductListDTO> productListDTOList = productService.selectProductList(offset, limit, categoryCode, sortCondition, minPrice, maxPrice);

        double totalItem = Long.valueOf(productService.countProductList()).doubleValue();
        int totalPage = (int) Math.ceil(totalItem / limit);

        if(page >= totalPage) {
            page = totalPage;
        } else if( page < 1) {
            page = 1;
        }

        Map<String, Map<String, Integer>> pagingBtn = JpqlPagingButton.JpqlPagingNumCount(page, totalPage);


        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("productList", productListDTOList);
        responseMap.put("pagingBtn", pagingBtn);

        ResponseMessage responseMessage = new ResponseMessage(200, "중고 상품 목록", responseMap);

        return new ResponseEntity<>(responseMessage, headers, HttpStatus.OK);
    }

    @ApiOperation("중고 상품 상세 조회")
    @GetMapping("/products/{productCode}")
    public ResponseEntity<ResponseMessage> selectProductInfo(@PathVariable int productCode) {

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        // 임시 번호 발급
        UUID uuid = UUID.fromString("52a9f8eb-7009-455b-b089-a9d374b06241");
        List<ProductDetailDTO> productDetailDTOList = productService.selectProductDetail(productCode);
        List<Integer> selectedWishCode = productService.selectWishCode(uuid, productCode);
        productService.updateViewCount(productCode);
        Map<String, String> selectedProductDetailImg = productService.selectProductDetailImg(productCode);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("productDetail", productDetailDTOList);
        responseMap.put("selectedWishCode", selectedWishCode);
        responseMap.put("selectedProductDetailImg", selectedProductDetailImg);

        ResponseMessage responseMessage = new ResponseMessage(200, "중고 상품 상세", responseMap);

        return new ResponseEntity<>(responseMessage, headers, HttpStatus.OK);
    }

    @ApiOperation(value = "관심 목록에 중고 상품 등록")
    @PostMapping("/products/{productCode}/wishLists")
    public ResponseEntity<?> registWishlist(@PathVariable int productCode) {
        // 임시 번호 발급
        UUID uuid = UUID.fromString("52a9f8eb-7009-455b-b089-a9d374b06241");
        WishListDTO wishListDTO = new WishListDTO();
        wishListDTO.setRefProductCode(productCode);
        wishListDTO.setRefUserCode(uuid);
        productService.insertWishList(wishListDTO);

        return ResponseEntity
                .created(URI.create("/products/" + productCode + "/wishLists")).build();
    }

    @PostMapping("/products/{productCode}/reports")
    public void registReport(@PathVariable int productCode) {

    }

    public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductDTO productDTO) {
        ProductDTO addedProduct = productService.addProduct(productDTO);

        return new ResponseEntity<>(addedProduct, HttpStatus.CREATED);
    }

    @PostMapping("/products/{productCode}/images")
    public ResponseEntity<ResponseMessage> addProductImages (
            @PathVariable int productCode,
            @RequestParam("imagesFiles") List<MultipartFile> imageFiles
    ) {
        //이미지 업로드 및 정보 저장 메서드 호출
        productService.addProductImage(productCode, imageFiles);
        // 필요한 응답 메시지 생성
        ResponseMessage responseMessage = new ResponseMessage(200, "이미지 추가 완료!", null);
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }
}
