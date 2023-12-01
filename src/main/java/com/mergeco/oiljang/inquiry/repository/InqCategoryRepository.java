package com.mergeco.oiljang.inquiry.repository;

import com.mergeco.oiljang.inquiry.entity.InqCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface InqCategoryRepository extends JpaRepository<InqCategory, Integer> {
    @Query(value = "SELECT inq_cate_code, inq_cate_name FROM inq_category ORDER BY inq_cate_code ASC", nativeQuery = true)
    public List<InqCategory> findAllCategory();
}
