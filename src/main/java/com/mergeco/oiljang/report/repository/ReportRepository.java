package com.mergeco.oiljang.report.repository;

import com.mergeco.oiljang.report.dto.ReportsDTO;
import com.mergeco.oiljang.report.entity.Report;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {

    @Query("SELECT r FROM tbl_report r WHERE r.reportUserNick LIKE %:search% ORDER BY r.reportNo DESC ")
    List<ReportsDTO> findByReportUserNickContaining(@Param("search") String search);

}
