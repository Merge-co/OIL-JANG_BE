package com.mergeco.oiljang.wishlist.service;

import com.mergeco.oiljang.common.paging.JpqlPagingButton;
import com.mergeco.oiljang.common.restApi.ResponseMessage;
import com.mergeco.oiljang.wishlist.controller.WishListController;
import com.mergeco.oiljang.wishlist.dto.WishListInfoDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@SpringBootTest
public class WishListServiceTests {

    @Autowired
    private WishListService wishListService;

    @Test
    void printWishListService() {
        Assertions.assertNotNull(wishListService);
    }

    @Test
    void selectWishList() {
        List<WishListInfoDTO> wishList = wishListService.selectWishList(0, 3, 1);
        System.out.println(wishList);
    }

    @Test
    @Transactional
    void deleteWishList() {
        wishListService.deleteWishList(1);
    }
}
