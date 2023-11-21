package com.mergeco.oiljang.product.controller;

import com.mergeco.oiljang.auth.model.DetailsUser;
import com.mergeco.oiljang.common.paging.JpqlPagingButton;
import com.mergeco.oiljang.common.restApi.ResponseMessage;
import com.mergeco.oiljang.product.dto.*;
import com.mergeco.oiljang.product.service.ProductService;
import com.mergeco.oiljang.user.entity.User;
import com.mergeco.oiljang.wishlist.dto.WishListDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.security.Principal;
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

        ResponseMessage responseMessage = new ResponseMessage(200, "카테고리 정보 조회 성공", responseMap);

        return new ResponseEntity<>(responseMessage, headers, HttpStatus.OK);
    }

    @ApiOperation(value = "중고 상품 목록 조회")
    @GetMapping("/products")
    public ResponseEntity<ResponseMessage> selectProductList(@RequestParam(required = false) Integer page, @RequestParam String pageKind, @RequestParam(required = false) Integer categoryCode, @RequestParam(required = false, defaultValue = "latest") String sortCondition, @RequestParam(required = false) Integer minPrice, @RequestParam(required = false) Integer maxPrice) {

        if (page == null) {
            page = 1;
        }

        if (categoryCode == null) {
            categoryCode = 6;
        }

        if (minPrice == null) {
            minPrice = -1;
        }

        if (maxPrice == null) {
            maxPrice = -1;
        }


        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        int limit = 0;
        switch (pageKind) {
            case "merge":
                limit = 6;
                break;
            case "list":
                limit = 8;
                break;
            case "main" :
                limit = 15;
                break;
        }

        int offset = limit * (page - 1);

        if(pageKind.equals("main")) {
            offset = 0;
            limit = limit * page;
        }

        List<ProductListDTO> productListDTOList = productService.selectProductList(offset, limit, categoryCode, sortCondition, minPrice, maxPrice);

        double totalItem = Long.valueOf(productService.countProductList(categoryCode, minPrice, maxPrice)).doubleValue();
        int totalPage = (int) Math.ceil(totalItem / limit);

        if (page >= totalPage) {
            page = totalPage;
        } else if (page < 1) {
            page = 1;
        }

        Map<String, Map<String, Integer>> pagingBtn = JpqlPagingButton.JpqlPagingNumCount(page, totalPage);


        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("productList", productListDTOList);
        responseMap.put("pagingBtn", pagingBtn);

        ResponseMessage responseMessage = new ResponseMessage(200, "중고 상품 목록 조회 성공", responseMap);

        return new ResponseEntity<>(responseMessage, headers, HttpStatus.OK);
    }

    @ApiOperation("중고 상품 상세 조회")
    @GetMapping("/products/{productCode}")
    public ResponseEntity<ResponseMessage> selectProductInfo(@PathVariable int productCode, String isView, String userCode) {
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));


        if(!Boolean.parseBoolean(isView) && isView != null) {
            productService.updateViewCount(productCode);
        }
        List<ProductDetailDTO> productDetailDTOList = productService.selectProductDetail(productCode);

        Map<String, String> selectedProductDetailImg = productService.selectProductDetailImg(productCode);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("productDetail", productDetailDTOList);
        responseMap.put("selectedProductDetailImg", selectedProductDetailImg);


        List<Integer> selectedWishCode;
        if (userCode != null) {
            selectedWishCode = productService.selectWishCode(Integer.parseInt(userCode), productCode);
            if(selectedWishCode.size() != 0) {
                responseMap.put("selectedWishCode", selectedWishCode);
            }
        }

        ResponseMessage responseMessage = new ResponseMessage(200, "중고 상품 상세 조회 성공", responseMap);

        return new ResponseEntity<>(responseMessage, headers, HttpStatus.OK);
    }

    @ApiOperation(value = "관심 목록에 중고 상품 등록")
    @PostMapping("/products/{productCode}/wishLists")
    public ResponseEntity<ResponseMessage> registWishlist(@PathVariable int productCode, Integer userCode) {

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        WishListDTO wishListDTO = new WishListDTO();
        wishListDTO.setRefProductCode(productCode);
        wishListDTO.setRefUserCode(2);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("result", productService.insertWishList(wishListDTO));

        ResponseMessage responseMessage = new ResponseMessage(200, "관심 목록 등록 성공", responseMap);

        return new ResponseEntity<>(responseMessage, headers, HttpStatus.OK);
    }

    @PostMapping("/products/{productCode}/reports")
    public void registReport(@PathVariable int productCode) {

    }

    public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductDTO productDTO) {
        ProductDTO addedProduct = productService.addProduct(productDTO);

        return new ResponseEntity<>(addedProduct, HttpStatus.CREATED);
    }


    @PostMapping("/products")
    public ResponseEntity<ResponseMessage> addProduct(
            @RequestParam("imagesFiles") List<MultipartFile> imageFiles,
            @ModelAttribute ProductDTO productDTO
    ) throws IOException {
        System.out.println("Received ProductDTO: " + productDTO);
        //이미지 업로드 및 정보 저장 메서드 호출
        int productCode = productService.addProduct(productDTO).getProductCode();
        productService.addProductImage(productCode, imageFiles);
        // 필요한 응답 메시지 생성
        ResponseMessage responseMessage = new ResponseMessage(200, "이미지 추가 완료!", null);
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }



    //상품 수정 API

    // 상품 삭제
    @ApiOperation(value = "상품 수정")
    @PutMapping("/products/{productCode}")
    public ResponseEntity<String> updateProduct(@PathVariable int productCode, @RequestBody ProductDTO productDTO) {
        try {
            System.out.println(productDTO);
            // 상품 수정 메서드를 ProductService에서 호출
            productService.updateProductInfo(productCode, productDTO);
            return new ResponseEntity<>("상품 수정이 완료되었습니다.", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("상품 수정에 실패했습니다. 상품을 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
        }
    }
    @ApiOperation(value = "중고 상품 삭제")
    @DeleteMapping(value = "/products/{productCode}")
    public ResponseEntity<ResponseMessage> deleteProduct(@PathVariable int productCode) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        productService.updateSellStatusToDeleted(productCode);

        ResponseMessage responseMessage = new ResponseMessage(200, "상품 삭제 성공", null);

        return new ResponseEntity<>(responseMessage, headers, HttpStatus.OK);
    }

    // 내 판매목록 조회
    @GetMapping("users/{userCode}/products")
    public ResponseEntity<ResponseMessage> selectSellingProduct(@PathVariable int userCode, @RequestParam(required = false) Integer page) {
        if (page == null) {
            page = 1;
        }

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        int limit = 3;
        int offset = limit * (page - 1);

        List<SellingListDTO> sellingList = productService.selectSellingList(offset, limit, userCode);

        double totalItem = Long.valueOf(productService.countSellingList(userCode)).doubleValue();
        int totalPage = (int) Math.ceil(totalItem / limit);

        if (page >= totalPage) {
            page = totalPage;
        } else if (page < 1) {
            page = 1;
        }

        Map<String, Map<String, Integer>> pagingBtn = JpqlPagingButton.JpqlPagingNumCount(page, totalPage);

        Map<String ,Object> responseMap = new HashMap<>();
        responseMap.put("sellingList",sellingList);
        responseMap.put("pagingBtn",pagingBtn);

        ResponseMessage responseMessage = new ResponseMessage(200, "판매 목록 조회 성공", responseMap);

        return new ResponseEntity<>(responseMessage, headers, HttpStatus.OK);

//        productService.selectSellingList();
    }
}
