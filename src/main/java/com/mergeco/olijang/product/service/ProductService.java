package com.mergeco.olijang.product.service;

import com.olijang.product.dto.ProductCategoryDTO;
import com.olijang.product.entity.ProductCategory;
import com.olijang.product.repository.CategoryRepository;
import com.olijang.product.repository.ProImageRepository;
import com.olijang.product.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ProImageRepository proImageRepository;
    private final ModelMapper modelMapper;

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
}
