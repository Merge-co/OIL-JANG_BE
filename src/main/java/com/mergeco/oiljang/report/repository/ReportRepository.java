package com.mergeco.oiljang.report.repository;

import com.mergeco.oiljang.report.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Integer> {
}
