package com.mergeco.oiljang.wishlist.controller;

import com.mergeco.oiljang.common.paging.JpqlPagingButton;
import com.mergeco.oiljang.common.restApi.ResponseMessage;
import com.mergeco.oiljang.wishlist.dto.WishListInfoDTO;
import com.mergeco.oiljang.wishlist.service.WishListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "관심 목록 관련")
@RestController
public class WishListController {

    private final WishListService wishListService;

    @Autowired
    public WishListController(WishListService wishListService) {
        this.wishListService = wishListService;
    }

    @ApiOperation("사용자의 관심 목록 조회")
    @GetMapping("users/{userCode}/wishLists")
    public ResponseEntity<ResponseMessage> selectWishList(@PathVariable int userCode, @RequestParam(required = false) Integer page) {

        if(page == null) {
            page = 1;
        }

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        int limit = 3;

        int offset = limit * (page - 1);
        List<WishListInfoDTO> wishListInfoDTOList = wishListService.selectWishList(offset, limit, userCode);

        double totalItem = Long.valueOf(wishListService.countWishList(userCode)).doubleValue();
        int totalPage = (int) Math.ceil(totalItem / limit);

        if(page >= totalPage) {
            page = totalPage;
        } else if( page < 1) {
            page = 1;
        }

        Map<String, Map<String, Integer>> pagingBtn = JpqlPagingButton.JpqlPagingNumCount(page, totalPage);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("wishList", wishListInfoDTOList);
        responseMap.put("pagingBtn", pagingBtn);

        ResponseMessage responseMessage = new ResponseMessage(200, "관심 목록 조회 성공", responseMap);

        return new ResponseEntity<>(responseMessage, headers, HttpStatus.OK);
    }

    @ApiOperation("사용자의 관심목록에서 관심 상품 삭제")
    @ApiResponses({
        @ApiResponse(code = 204, message = "관심 상품 삭제 성공"),
        @ApiResponse(code = 404, message = "파라미터 값 입력이 잘못됨")
    })
    @DeleteMapping("wishLists/{wishCode}")
    public ResponseEntity<ResponseMessage> deleteWishList(@PathVariable int wishCode) {

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("result", wishListService.deleteWishList(wishCode));

        ResponseMessage responseMessage = new ResponseMessage(200, "관심 목록에서 찜 삭제 성공", responseMap);

        return new ResponseEntity<>(responseMessage, headers, HttpStatus.OK);
    }
}
