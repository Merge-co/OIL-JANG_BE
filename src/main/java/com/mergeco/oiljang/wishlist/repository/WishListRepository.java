package com.mergeco.oiljang.wishlist.repository;

import com.mergeco.oiljang.wishlist.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface WishListRepository extends JpaRepository<WishList, Integer> {
}
