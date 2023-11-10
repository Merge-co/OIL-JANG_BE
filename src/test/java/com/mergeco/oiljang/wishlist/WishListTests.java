package com.mergeco.oiljang.wishlist;

import com.mergeco.oiljang.common.paging.JpqlPagingButton;
import com.mergeco.oiljang.common.restApi.ResponseMessage;
import com.mergeco.oiljang.wishlist.controller.WishListController;
import com.mergeco.oiljang.wishlist.dto.WishListInfoDTO;
import com.mergeco.oiljang.wishlist.service.WishListService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;


@SpringBootTest
public class WishListTests {

    @Autowired
    private WishListService wishListService;

    @Autowired
    private WishListController wishListController;

    @Test
    void printWishListService() {
        Assertions.assertNotNull(wishListService);
    }

    @Test
    void selectWishList() {
        UUID uuid = UUID.fromString("52a9f8eb-7009-455b-b089-a9d374b06241");
        List<WishListInfoDTO> wishList = wishListService.selectWishList(0, 3, uuid);
        System.out.println(wishList);
    }

    @Test
    @Transactional
    void deleteWishList() {
        wishListService.deleteWishList(1);
    }

    @Test
    void pagingTest() {
        Map<String, Map<String, Integer>> pagingBtn = JpqlPagingButton.JpqlPagingNumCount(4, 20);
        for(String key : pagingBtn.keySet()) {
            System.out.println(key + " : " + pagingBtn.get(key));
        }
        System.out.println();
        pagingBtn = JpqlPagingButton.JpqlPagingNumCount(1, 20);
        for(String key : pagingBtn.keySet()) {
            System.out.println(key + " : " + pagingBtn.get(key));
        }
        System.out.println();
        pagingBtn = JpqlPagingButton.JpqlPagingNumCount(5, 20);
        for(String key : pagingBtn.keySet()) {
            System.out.println(key + " : " + pagingBtn.get(key));
        }
        System.out.println();
        pagingBtn = JpqlPagingButton.JpqlPagingNumCount(6, 20);
        for(String key : pagingBtn.keySet()) {
            System.out.println(key + " : " + pagingBtn.get(key));
        }
        System.out.println();
        pagingBtn = JpqlPagingButton.JpqlPagingNumCount(15, 20);
        for(String key : pagingBtn.keySet()) {
            System.out.println(key + " : " + pagingBtn.get(key));
        }
        System.out.println();
        pagingBtn = JpqlPagingButton.JpqlPagingNumCount(16, 20);
        for(String key : pagingBtn.keySet()) {
            System.out.println(key + " : " + pagingBtn.get(key));
        }
        System.out.println();
        pagingBtn = JpqlPagingButton.JpqlPagingNumCount(20, 20);
        for(String key : pagingBtn.keySet()) {
            System.out.println(key + " : " + pagingBtn.get(key));
        }
        System.out.println();
        pagingBtn = JpqlPagingButton.JpqlPagingNumCount(20, 21);
        for(String key : pagingBtn.keySet()) {
            System.out.println(key + " : " + pagingBtn.get(key));
        }

    }

    @Test
    void printWishController() {
        System.out.println(wishListController);
    }

    @Test
    void controllerSelectWishList() {
        UUID uuid = UUID.fromString("52a9f8eb-7009-455b-b089-a9d374b06241");
        ResponseEntity<ResponseMessage> result = wishListController.selectWishList(uuid, 1);
        Assertions.assertEquals(result.getStatusCodeValue(), 200);
        Assertions.assertEquals(result.getBody().getMessage(), "관심 목록 조회 성공");
        Assertions.assertTrue(result.getBody().getResults().size() > 0);
    }

    @Test
    @Transactional
    void controllerDeleteWishList() {
        ResponseEntity<ResponseMessage> result = wishListController.deleteWishList(1);
        Assertions.assertEquals(result.getStatusCodeValue(), 200);
        Assertions.assertEquals(result.getBody().getMessage(), "관심 목록에서 찜 삭제 성공");
        Assertions.assertTrue(result.getBody().getResults().size() > 0);
    }

}
