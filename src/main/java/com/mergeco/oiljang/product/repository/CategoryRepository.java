package com.mergeco.oiljang.product.repository;

import com.mergeco.oiljang.product.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query(value = "SELECT category_code, category_name,upper_category_code FROM category_info ORDER BY category_code ASC"
        , nativeQuery = true)
    public List<Category> findAllCategory();
}
