package com.mergeco.oiljang.wishlist;

import com.mergeco.oiljang.wishlist.dto.WishListInfoDTO;
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
        UUID uuid = UUID.fromString("52a9f8eb-7009-455b-b089-a9d374b06241");
        List<WishListInfoDTO> wishList = wishListService.selectWishList(0, 3, uuid);
        System.out.println(wishList);
    }

    // 값이 있을 경우에 제대로 작동 한다.
    @Test
    void deleteWishList() {
        wishListService.deleteWishList(4);
    }
}
