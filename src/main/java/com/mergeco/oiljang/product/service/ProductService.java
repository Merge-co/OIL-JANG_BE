package com.mergeco.oiljang.product.service;


import com.mergeco.oiljang.product.dto.CategoryDTO;
import com.mergeco.oiljang.product.dto.ProductDTO;

import com.mergeco.oiljang.product.entity.Category;
import com.mergeco.oiljang.product.entity.Product;
import com.mergeco.oiljang.product.repository.ProImageRepository;
import com.mergeco.oiljang.product.repository.ProductRepository;
import com.mergeco.oiljang.product.repository.CategoryRepository;
import com.mergeco.oiljang.wishlist.repository.WishListRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class ProductService {

    @PersistenceContext
    private final EntityManager entityManager;

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;
    private final ProImageRepository proImageRepository;
    private final WishListRepository wishListRepository;

    @Autowired
    public ProductService(EntityManager entityManager, ProductRepository productRepository, ModelMapper modelMapper, CategoryRepository categoryRepository, ProImageRepository proImageRepository, WishListRepository wishListRepository) {
        this.entityManager = entityManager;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
        this.proImageRepository = proImageRepository;
        this.wishListRepository = wishListRepository;
    }

    public ProductDTO addProduct(ProductDTO productDTO) {
        //ProductDTO를 Product Entity로 변환
        Product product = convertToEntity(productDTO);

        //Rroduct를 DB에 저장
        Product saveProduct = productRepository.save(product);

        //저장된 Product를 다시 DTO로 변환하여 변환
        return convertToDTO(saveProduct);
    }

    private Product convertToEntity(ProductDTO productDTO) {
        return modelMapper.map(productDTO, Product.class);
    }

    private ProductDTO convertToDTO(Product product) {
        return modelMapper.map(product, ProductDTO.class);
    }

    public List<CategoryDTO> findAllCategory() {
        List<Category> categoryList = categoryRepository.findAllCategory();

        return categoryList.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .collect(Collectors.toList());
    }

    public List<Object[]> selectProductList(int offset, int limit, int categoryCode, String sortCondition, int minPrice, int maxPrice) {
        StringBuilder jpql = new StringBuilder("SELECT m.productCode, m.productName, m.productPrice, m.enrollDateTime, m.Category.categoryName FROM Product m JOIN m.Category c WHERE m.Category.categoryCode = :categoryCode");

        if(minPrice >= 0) {
            jpql.append(" AND m.productPrice >= :minPrice");
        }

        if(maxPrice >= 0) {
            jpql.append(" AND m.productPrice <= :maxPrice");
        }

        if(!"".equals(sortCondition) && sortCondition != null) {
            jpql.append(" ORDER BY");
            switch (sortCondition) {
                case "latest":
                    jpql.append(" m.enrollDateTime DESC");
                    break;
                case "minPrice":
                    jpql.append(" m.productPrice ASC");
                    break;
                case "maxPrice":
                    jpql.append(" m.productPrice DESC");
                    break;
            }
        }

        TypedQuery<Object[]> query = (TypedQuery<Object[]>) entityManager.createQuery(jpql.toString());

        query.setParameter("categoryCode" ,categoryCode);
        query.setFirstResult(offset)
                .setMaxResults(limit);

        if(minPrice >= 0) {
            query.setParameter("minPrice" ,minPrice);
        }

        if(maxPrice >= 0) {
            query.setParameter("maxPrice" ,maxPrice);
        }



        List<Object[]> productList = query.getResultList();

        return productList;
    }

    // refUserCode 나중에 판매자 이름 추츨 해야 한다.
    public List<Object[]> selectProductDetail(int productCode) {
        String jpql ="SELECT m.productName, m.productPrice, m.enrollDateTime, m.Category.categoryName, m.viewCount, (SELECT Count(w.wishCode) FROM WishList w WHERE w.refProductCode = :productCode), m.refUserCode, m.productDesc, m.wishPlaceTrade FROM Product m WHERE m.productCode = :productCode";
        List<Object[]> productDetail = entityManager.createQuery(jpql).setParameter("productCode", productCode).getResultList();
        return productDetail;
    }

    public List<Integer> selectWishCode(UUID refUserCode, int productCode) {
        String jpql = "SELECT w.wishCode FROM WishList w WHERE refUserCode = :refUserCode AND refProductCode = :productCode";
        List<Integer> wishCode = entityManager.createQuery(jpql)
                .setParameter("refUserCode", refUserCode)
                .setParameter("productCode", productCode)
                .getResultList();
        return wishCode;
    }



}
