package com.mergeco.oiljang.wishlist.service;

import com.mergeco.oiljang.wishlist.dto.WishListInfoDTO;
import com.mergeco.oiljang.wishlist.repository.WishListRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@SpringBootTest
public class WishListServiceTests {

    @Autowired
    private WishListService wishListService;

    @Autowired
    private WishListRepository wishListRepository;

    @Test
    void printWishListService() {
        Assertions.assertNotNull(wishListService);
    }

    @Test
    void selectWishList() {

        //when
        List<WishListInfoDTO> wishList = wishListService.selectWishList(0, 3, 1);

        //then
        Assertions.assertNotNull(wishList);
    }

    @Test
    @Transactional
    void deleteWishList() {

        //when
        wishListService.deleteWishList(1);

        //then
        Assertions.assertFalse(wishListRepository.existsById(1));
    }
}
