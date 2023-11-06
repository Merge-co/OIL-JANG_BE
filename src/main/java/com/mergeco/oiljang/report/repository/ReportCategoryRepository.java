package com.mergeco.oiljang.report.repository;

import com.mergeco.oiljang.report.entity.ReportCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportCategoryRepository extends JpaRepository<ReportCategory, Integer> {

}