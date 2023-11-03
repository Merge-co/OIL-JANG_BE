package com.mergeco.olijang.product1.repository1;

import com.mergeco.olijang.product1.entity1.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<ProductCategory, Integer> {
}
