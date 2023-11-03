package com.mergeco.olijang.product.repository1;

import com.mergeco.olijang.product.entity1.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
