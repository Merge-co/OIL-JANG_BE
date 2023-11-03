package com.mergeco.olijang.product.service;


import com.mergeco.olijang.product.dto.CategoryDTO;
import com.mergeco.olijang.product.dto.ProductDTO;
import com.mergeco.olijang.product.entity.Category;
import com.mergeco.olijang.product.entity.Product;
import com.mergeco.olijang.product.repository.ProductRepository;
import com.mergeco.olijang.product.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, ModelMapper modelMapper, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
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

}
