package com.mergeco.olijang.report.repository;

import com.mergeco.olijang.report.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Integer> {
}
