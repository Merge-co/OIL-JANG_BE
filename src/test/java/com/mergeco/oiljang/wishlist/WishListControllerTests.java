package com.mergeco.oiljang.wishlist;

import com.mergeco.oiljang.wishlist.dto.WishListDTO;
import com.mergeco.oiljang.wishlist.service.WishListService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;


@SpringBootTest
public class WishListControllerTests {

    @Autowired
    private WishListService wishListService;

    @Test
    void printWishListService() {
        Assertions.assertNotNull(wishListService);
    }

    @Test
    void selectWishList() {
        List<WishListDTO> wishList = wishListService.selectWishList(UUID.randomUUID());
        System.out.println(wishList);
    }

    // 값이 있을 경우에 제대로 작동 한다.
    @Test
    void deleteWishList() {
        wishListService.deleteWishList(2);
    }
}
