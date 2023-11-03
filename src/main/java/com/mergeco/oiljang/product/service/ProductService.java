package com.mergeco.oiljang.product.service;


import com.mergeco.oiljang.product.dto.CategoryDTO;
import com.mergeco.oiljang.product.dto.ProductDTO;

import com.mergeco.oiljang.product.entity.Category;
import com.mergeco.oiljang.product.entity.Product;
import com.mergeco.oiljang.product.repository.ProImageRepository;
import com.mergeco.oiljang.product.repository.ProductRepository;
import com.mergeco.oiljang.product.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ProductService {

    @PersistenceContext
    EntityManager entityManager;

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    private final CategoryRepository categoryRepository;

    private final ProImageRepository proImageRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, ModelMapper modelMapper, CategoryRepository categoryRepository, ProImageRepository proImageRepository) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
        this.proImageRepository = proImageRepository;
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

    public List<Object[]> selectProductList() {
        String jpql ="SELECT m.productCode, m.productName, m.productPrice, m.enrollDateTime, m.Category.categoryName FROM Product m JOIN m.Category c WHERE m.Category.categoryCode = 1";
        List<Object[]> productList = entityManager.createQuery(jpql).getResultList();
        return productList;
    }

}
