package com.mergeco.oiljang.product.service;


import com.mergeco.oiljang.product.dto.CategoryDTO;
import com.mergeco.oiljang.product.dto.ProductDTO;

import com.mergeco.oiljang.product.dto.ProductDetailDTO;
import com.mergeco.oiljang.product.dto.ProductListDTO;
import com.mergeco.oiljang.product.entity.Category;
import com.mergeco.oiljang.product.entity.Product;
import com.mergeco.oiljang.product.repository.ProImageRepository;
import com.mergeco.oiljang.product.repository.ProductRepository;
import com.mergeco.oiljang.product.repository.CategoryRepository;
import com.mergeco.oiljang.user.repository.UserProfileRepository;
import com.mergeco.oiljang.wishlist.dto.WishListDTO;
import com.mergeco.oiljang.wishlist.entity.WishList;
import com.mergeco.oiljang.wishlist.repository.WishListRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
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
    private final UserProfileRepository userProfileRepository;

    @Autowired
    public ProductService(EntityManager entityManager, ProductRepository productRepository, ModelMapper modelMapper, CategoryRepository categoryRepository, ProImageRepository proImageRepository, WishListRepository wishListRepository, UserProfileRepository userProfileRepository) {
        this.entityManager = entityManager;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
        this.proImageRepository = proImageRepository;
        this.wishListRepository = wishListRepository;
        this.userProfileRepository = userProfileRepository;
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

    public List<ProductListDTO> selectProductList(int offset, int limit, int categoryCode, String sortCondition, int minPrice, int maxPrice) {
        StringBuilder jpql = new StringBuilder("SELECT new com.mergeco.oiljang.product.dto.ProductListDTO(m.productCode, (SELECT p.proImageThumbAddr FROM ProImageInfo p WHERE p.refProductCode = m.productCode), m.productName, m.productPrice, m.enrollDateTime, s.sellStatus)" +
                " FROM Product m JOIN m.Category c JOIN m.SellStatus s WHERE m.Category.categoryCode = :categoryCode");

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

        TypedQuery<ProductListDTO> query = (TypedQuery<ProductListDTO>) entityManager.createQuery(jpql.toString(), ProductListDTO.class);

        query.setParameter("categoryCode" ,categoryCode);
        query.setFirstResult(offset)
                .setMaxResults(limit);

        if(minPrice >= 0) {
            query.setParameter("minPrice" ,minPrice);
        }

        if(maxPrice >= 0) {
            query.setParameter("maxPrice" ,maxPrice);
        }

        List<ProductListDTO> productListDTO = query.getResultList();

        return productListDTO;
    }

    // refUserCode 나중에 판매자 이름 추츨 해야 한다.
    public List<ProductDetailDTO> selectProductDetail(int productCode) {
        String jpql ="SELECT new com.mergeco.oiljang.product.dto.ProductDetailDTO(m.productCode, (SELECT p.proImageOriginAddr FROM ProImageInfo p WHERE p.refProductCode = m.productCode), m.productName, m.productPrice, m.Category.categoryName, (SELECT c.categoryName FROM Category c WHERE c.categoryCode = m.Category.upperCategoryCode), m.enrollDateTime, m.viewCount, (SELECT Count(w.wishCode) FROM WishList w WHERE w.product.productCode = :productCode), m.refUserCode, (SELECT up.userImageThumbAddr FROM UserProfile up WHERE up.refUserCode = m.refUserCode) ,(SELECT u.nickname FROM User u WHERE u.userId = m.refUserCode), m.productDesc, m.wishPlaceTrade, s.sellStatus)" +
                " FROM Product m JOIN m.SellStatus s WHERE m.productCode = :productCode";
        List<ProductDetailDTO> productDetailDTOS = entityManager.createQuery(jpql, ProductDetailDTO.class).setParameter("productCode", productCode).getResultList();
        return productDetailDTOS;
    }
    public List<Integer> selectWishCode(UUID refUserCode, int productCode) {
        String jpql = "SELECT w.wishCode FROM WishList w WHERE refUserCode = :refUserCode AND w.product.productCode = :productCode";
        List<Integer> wishCode = entityManager.createQuery(jpql)
                .setParameter("refUserCode", refUserCode)
                .setParameter("productCode", productCode)
                .getResultList();
        return wishCode;
    }

    @Transactional
    public void updateViewCount(int productCode) {
        Product product = productRepository.findById(productCode).orElseThrow(IllegalArgumentException::new);
        Product productSave = product.viewCount(product.getViewCount() + 1).builder();
        productRepository.save(productSave);
    }

    @Transactional
    public void insertWishList(WishListDTO wishListDTO) {
        wishListRepository.save(modelMapper.map(wishListDTO, WishList.class));
    }

    public void updateTest() {
        Product product = productRepository.findById(6).orElseThrow(IllegalArgumentException::new);
        Product productSave = product.category(20);
        productRepository.save(productSave);
    }

}
