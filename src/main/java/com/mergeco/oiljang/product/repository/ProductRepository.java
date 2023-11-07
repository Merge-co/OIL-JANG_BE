package com.mergeco.oiljang.product.repository;

import com.mergeco.oiljang.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface ProductRepository extends JpaRepository<Product, Integer> {


}
