package com.mergeco.oiljang.report.repository;

import com.mergeco.oiljang.report.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {


}
