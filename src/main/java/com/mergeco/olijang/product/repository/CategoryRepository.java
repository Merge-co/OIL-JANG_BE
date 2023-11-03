package com.mergeco.olijang.product.repository;

import com.mergeco.olijang.product.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query(value = "SELECT category_code, category_name,upper_category_code FROM tbl_category ORDER BY category_code ASC"
        , nativeQuery = true)
    public List<Category> findAllCategory();
}
