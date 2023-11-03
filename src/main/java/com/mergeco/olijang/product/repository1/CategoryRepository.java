package com.mergeco.olijang.product.repository1;

import com.mergeco.olijang.product.entity1.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<ProductCategory, Integer> {
}
