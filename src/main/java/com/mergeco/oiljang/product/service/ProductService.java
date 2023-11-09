package com.mergeco.oiljang.product.service;


import com.mergeco.oiljang.product.dto.*;

import com.mergeco.oiljang.product.entity.Category;
import com.mergeco.oiljang.product.entity.ProImageInfo;
import com.mergeco.oiljang.product.entity.Product;
import com.mergeco.oiljang.product.repository.ProImageRepository;
import com.mergeco.oiljang.product.repository.ProductRepository;
import com.mergeco.oiljang.product.repository.CategoryRepository;
import com.mergeco.oiljang.wishlist.dto.WishListDTO;
import com.mergeco.oiljang.wishlist.entity.WishList;
import com.mergeco.oiljang.wishlist.repository.WishListRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public Long countProductList() {
        Long countPage = productRepository.count();
        return countPage;
    }

    public List<ProductListDTO> selectProductList(int offset, int limit, int categoryCode, String sortCondition, int minPrice, int maxPrice) {
        StringBuilder jpql = new StringBuilder("SELECT new com.mergeco.oiljang.product.dto.ProductListDTO(m.productCode, m.productThumbAddr, m.productName, m.productPrice, m.enrollDateTime, s.sellStatus)" +
                " FROM Product m JOIN m.Category c JOIN m.SellStatus s WHERE m.Category.categoryCode = :categoryCode AND s.sellStatusCode = 1");

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
        String jpql ="SELECT new com.mergeco.oiljang.product.dto.ProductDetailDTO(m.productCode, m.productName, m.productPrice, m.Category.categoryName, (SELECT c.categoryName FROM Category c WHERE c.categoryCode = m.Category.upperCategoryCode), m.enrollDateTime, m.viewCount, (SELECT Count(w.wishCode) FROM WishList w WHERE w.product.productCode = :productCode), m.refUserCode, (SELECT up.userImageThumbAddr FROM UserProfile up WHERE up.refUserCode = m.refUserCode) ,(SELECT u.nickname FROM User u WHERE u.userCode = m.refUserCode), m.productDesc, m.wishPlaceTrade, s.sellStatus)" +
                " FROM Product m JOIN m.SellStatus s WHERE m.productCode = :productCode";
        List<ProductDetailDTO> productDetailDTOS = entityManager.createQuery(jpql, ProductDetailDTO.class).setParameter("productCode", productCode).getResultList();
        return productDetailDTOS;
    }
    public Map<String, String> selectProductDetailImg(int productCode) {
        String jpql = "SELECT p.proImageOriginName FROM ProImageInfo p WHERE p.refProductCode = :productCode ORDER BY p.proImageCode ASC";
        List<String> selectProductDetailImgAddr = entityManager.createQuery(jpql).setParameter("productCode", productCode).getResultList();
        Map<String, String> selectProductDetailImg = new HashMap<>();
        int detailImgOrder = 1;
        for(String imgAddr : selectProductDetailImgAddr) {
            selectProductDetailImg.put("detailImg" + detailImgOrder, imgAddr);
            detailImgOrder++;
        }
        return selectProductDetailImg;
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

//    public void updateTest() {
//        Product product = productRepository.findById(6).orElseThrow(IllegalArgumentException::new);
//        Product productSave = product.category(20);
//        productRepository.save(productSave);
//    }

    public void addProductImage(int productCode, List<MultipartFile> imageFiles) {
        if(imageFiles != null && imageFiles.size() <= 5) {
            for (MultipartFile imageFile : imageFiles) {
                //이미지 업로드 및 정보 저장
                String imageAddress = saveImage(imageFile);
                ProImageInfoDTO imageInfo = new ProImageInfoDTO();
                imageInfo.setRefProductCode(productCode);
                imageInfo.setProImageOriginName(imageFile.getOriginalFilename());
                imageInfo.setProImageDbName(imageAddress);
                imageInfo.setProImageOriginAddr(imageAddress);
                // 이미지 정보를 데이터베이스에 저장
                addProImageInfo(imageInfo);
            }
        }
    }
    public String saveImage(MultipartFile imageFile) {
        // 이미지 파일을 서버에 저장하고 파일 주소를 변환
        // 실제 파일 저장 및 주소 생성 로직을 구현
        try {
            System.out.println(22222);
            byte[] bytes = imageFile.getBytes();
            String fileName = imageFile.getName(); // 사용자가 업로드한 파일명
            String filePath = "/Users/minbumkim/Desktop/test/" + fileName; // 실제 이미지를 저장할 경로
            Path path = Paths.get(filePath);
            Files.write(path, bytes);
            System.out.println(22222);
            return filePath; // 저장된 이미지 파일 경로 반환
        } catch (IOException e) {
            //오륲 처리
            System.out.println(22222);
            e.printStackTrace();
            return null;
        }
    }

    public void addProImageInfo(ProImageInfoDTO imageInfo) {
    }

    /*민범님*/
    // 상품 정보 업데이트
    @Transactional
    public void updateProductInfo(int productCode, ProductDTO updatedProductDTO) {
        // 업데이트할 상품을 조회
        Product existingProduct = productRepository.findById(updatedProductDTO.getProductCode())
                .orElseThrow(IllegalArgumentException::new);

        // 새로운 정보로 업데이트
        existingProduct.productName(updatedProductDTO.getProductName());
        existingProduct.productPrice(updatedProductDTO.getProductPrice());
        existingProduct.productDesc(updatedProductDTO.getProductDesc());

        //상품 업데이트
        productRepository.save(existingProduct);
    }

    // 상품 이미지 추가
    @Transactional
    public void addProductImage(int productCode, MultipartFile imageFile) {
        //이미지 업로드 정보 저장
        String imageAddress = saveImage(imageFile);

        // 이미지 정보를 데이터베이스에 저장
        ProImageInfoDTO imageInfo = new ProImageInfoDTO();
        imageInfo.setRefProductCode(productCode);
        imageInfo.setProImageOriginName(imageFile.getOriginalFilename());
        imageInfo.setProImageOriginAddr(imageAddress);
        addProImageInfo(imageInfo);
    }

    //상품 이미지 삭제
    @Transactional
    public void deleteProductImage(int imageCode) {
        // 이미지 정보 조회
        ProImageInfo imageInfo = proImageRepository.findById(imageCode)
                .orElseThrow(IllegalArgumentException::new);

        //이미지 파일 삭제 (옵션)
        deleteImageFile(imageInfo.getProImageDbName());

        //데이터베이스에서 이미지 정보 삭제
        proImageRepository.delete(imageInfo);
    }

    public void deleteImageFile(String imageFilePath) {
        // 이미지 파일 경로로 File 객체 생성
        File imageFile = new File(imageFilePath);

        // 파일이 존재하는지 확인
        if (imageFile.exists()) {
            // 파일을 삭제
            if (imageFile.delete()) {
                System.out.println("이미지 파일 삭제 성공: " + imageFilePath);
            } else {
                System.out.println("이미지 파일 삭제 실패: " + imageFilePath);
            }
        } else {
            System.out.println("존재하지 않는 이미지 파일: " + imageFilePath);
        }
    }

//    public List<ProductListDTO> selectSellingProductList(int offset, int limit, Integer categoryCode, String sortCondition, Integer minPrice, Integer maxPrice) {
//
//        String jpql = "SELECT new com.mergeco.oiljang.product.dto.ProductListDTO(p.productCode, p.productName, p.productPrice, p.productThumbAddr) " +
//                "FROM Product p " +
//                "JOIN p.Category c " +
//                "JOIN p.SellStatus s " + // Added a space before "WHERE"
//                "WHERE c.categoryCode = :categoryCode " + // Added a space before "AND"
//                "AND s.sellStatusCode = 1";
//
//        if(minPrice >= 0) {
//            jpql += " AND p.productPrice >= :minPrice"; // Added a space before "AND"
//        }
//
//        if (maxPrice >= 0) {
//            jpql += " AND p.productPrice <= :maxPrice"; // Added a space before "AND"
//        }
//
//        if ("latest".equals(sortCondition)) {
//            jpql += " ORDER BY p.enrollDateTime DESC";
//        } else if ("minPrice".equals(sortCondition)) {
//            jpql += " ORDER BY p.productPrice DESC";
//        }
//
//        TypedQuery<ProductListDTO> query = entityManager.createQuery(jpql, ProductListDTO.class)
//                .setParameter("categoryCode", categoryCode);
//
//        if (minPrice >= 0) {
//            query.setParameter("minPrice", minPrice);
//        }
//
//        if (maxPrice >= 0) {
//            query.setParameter("maxPrice", maxPrice);
//        }
//
//        List<ProductListDTO> totalSellingProducts = query.getResultList(); // Changed from getSingleResult to getResultList
//
//        return totalSellingProducts;
//    }


//    public long countSellingProductList() {
//    // 판매 중인 상품 수 조회 쿼리
//        String jpql = "SELECT COUNT(p.productCode) " +
//                "FROM Product p " +
//                "JOIN p.Category c " +
//                "JOIN p.SellStatus s " +
//                "WHERE c.categoryCode = :categoryCode " +
//                "AND s.sellStatusCode = 1";
//        int minPrice;
//        if (minPrice >= 0) {
//            jpql += " AND p.productPrice >= :minPrice";
//        }
//
//        if (maxPrice >= 0) {
//            jpql += " AND p.productPrice <= :maxPrice";
//        }
//
//        TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class)
//                .setParameter("categoryCode", categoryCode);
//
//        if (minPrice >= 0) {
//            query.setParameter("minPrice", minPrice);
//        }
//
//        if (maxPrice >= 0) {
//            query.setParameter("maxPrice", maxPrice);
//        }
//
//        Long totalSellingProducts = query.getSingleResult();
//
//        return totalSellingProducts;
//    }
}
