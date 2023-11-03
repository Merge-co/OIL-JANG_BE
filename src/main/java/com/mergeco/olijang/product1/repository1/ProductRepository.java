package com.mergeco.olijang.product1.repository1;

import com.mergeco.olijang.product1.entity1.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
