package com.mergeco.oiljang.wishlist.service;

import com.mergeco.oiljang.wishlist.dto.WishListInfoDTO;
import com.mergeco.oiljang.wishlist.entity.WishList;
import com.mergeco.oiljang.wishlist.repository.WishListRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class WishListService {

    @PersistenceContext
    private final EntityManager entityManager;
    private final ModelMapper modelMapper;
    private final WishListRepository wishListRepository;

    @Autowired
    public WishListService(EntityManager entityManager, ModelMapper modelMapper, WishListRepository wishListRepository) {
        this.entityManager = entityManager;
        this.modelMapper = modelMapper;
        this.wishListRepository = wishListRepository;
    }

    public Long countWishList(int refUserCode) {
        return wishListRepository.countByRefUserCode(refUserCode);
    }

    public List<WishListInfoDTO> selectWishList(int offset, int limit, int refUserCode) {
        String jpql = "SELECT new com.mergeco.oiljang.wishlist.dto.WishListInfoDTO(w.wishCode, p.productThumbAddr, p.SellStatus.sellStatus, p.productName, p.productPrice, p.productDesc, p.productCode)" +
                " FROM WishList w JOIN w.product p WHERE w.refUserCode = :refUserCode ORDER BY w.wishCode DESC";
        List<WishListInfoDTO> wishList = entityManager.createQuery(jpql)
                .setParameter("refUserCode", refUserCode)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
        return wishList;
    }

    @Transactional
    public String deleteWishList(int wishCode) {
        String result = "관심 목록에서 찜 삭제 실패";
        WishList wishList = wishListRepository.findById(wishCode).orElse(null);
        if(wishList != null) {
            wishListRepository.delete(wishList);
        }
        result = "관심 목록에서 찜 삭제 성공";
        return result;
    }
}
