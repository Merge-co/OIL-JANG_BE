package com.mergeco.olijang.product.repository;

import com.mergeco.olijang.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
