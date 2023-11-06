package com.mergeco.oiljang.inquery.repository;

import com.mergeco.oiljang.inquery.dto.InqCategoryDTO;
import com.mergeco.oiljang.inquery.entity.InqCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface InqCategoryRepository extends JpaRepository<InqCategory, Integer> {
}
