package com.mergeco.olijang.product.service1;

import com.mergeco.olijang.product.dto1.ProductCategoryDTO;
import com.mergeco.olijang.product.entity1.Product;
import com.mergeco.olijang.product.entity1.ProductCategory;
import com.mergeco.olijang.product.repository1.CategoryRepository;
import com.mergeco.olijang.product.repository1.ProImageRepository;
import com.mergeco.olijang.product.repository1.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ProImageRepository proImageRepository;
    private final ModelMapper modelMapper;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public ProductService(CategoryRepository categoryRepository, ProductRepository productRepository, ProImageRepository proImageRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.proImageRepository = proImageRepository;
        this.modelMapper = modelMapper;
    }

    public List<ProductCategoryDTO> findCategoryList() {
        List<ProductCategory> productCategory = categoryRepository.findAll();

        return productCategory.stream()
                .map(category -> modelMapper.map(category, ProductCategoryDTO.class))
                .collect(Collectors.toList());
    }

    public List<Product> selectProductList() {
        String jpql ="SELECT m FROM Product m JOIN m.productCategory c";
        List<Product> productList = entityManager.createQuery(jpql, Product.class).getResultList();
        return productList;
    }




}
