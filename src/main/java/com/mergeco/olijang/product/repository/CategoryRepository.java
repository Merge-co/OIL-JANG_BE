package com.mergeco.olijang.product.repository;

import com.mergeco.olijang.product.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<ProductCategory, Integer> {
}
