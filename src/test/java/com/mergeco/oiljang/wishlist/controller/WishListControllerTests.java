package com.mergeco.oiljang.wishlist.controller;

import com.mergeco.oiljang.common.restApi.ResponseMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;



@SpringBootTest
public class WishListControllerTests {

    @Autowired
    private WishListController wishListController;

    @Test
    void printWishController() {
        System.out.println(wishListController);
    }

    @Test
    void controllerSelectWishList() {

        //when
        ResponseEntity<ResponseMessage> result = wishListController.selectWishList(1, 1);

        //then
        Assertions.assertEquals(result.getStatusCodeValue(), 200);
        Assertions.assertEquals(result.getBody().getMessage(), "관심 목록 조회 성공");
        Assertions.assertTrue(result.getBody().getResults().size() > 0);
    }

    @Test
    @Transactional
    void controllerDeleteWishList() {

        //when
        ResponseEntity<ResponseMessage> result = wishListController.deleteWishList(1);

        //then
        Assertions.assertEquals(result.getStatusCodeValue(), 200);
        Assertions.assertEquals(result.getBody().getMessage(), "관심 목록에서 찜 삭제 성공");
        Assertions.assertTrue(result.getBody().getResults().size() > 0);
    }

}
