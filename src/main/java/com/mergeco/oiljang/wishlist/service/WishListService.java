package com.mergeco.oiljang.wishlist.service;

import com.mergeco.oiljang.wishlist.dto.WishListDTO;
import com.mergeco.oiljang.wishlist.repository.WishListRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

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

    public List<WishListDTO> selectWishList(int offset, int limit, UUID refUserCode) {
        String jpql = "SELECT w from WishList w WHERE refUserCode = :refUserCode ORDER BY w.wishCode DESC";
        List<WishListDTO> wishList = entityManager.createQuery(jpql)
                .setParameter("refUserCode", refUserCode)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
        return wishList;
    }

    @Transactional
    public void deleteWishList(int wishListCode) {
        wishListRepository.deleteById(wishListCode);
    }
}
