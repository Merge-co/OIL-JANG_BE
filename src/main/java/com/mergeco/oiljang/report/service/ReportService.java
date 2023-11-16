package com.mergeco.oiljang.report.service;

import com.mergeco.oiljang.product.entity.SellStatus;
import com.mergeco.oiljang.product.repository.ProductRepository;
import com.mergeco.oiljang.report.entity.Report;
import com.mergeco.oiljang.report.dto.ReportDTO;
import com.mergeco.oiljang.report.repository.ReportRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.persistence.*;
import java.util.List;

@Service
@Slf4j
public class ReportService {

    @PersistenceContext
    private final EntityManager manager;
    private final ReportRepository reportRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ReportService(EntityManager manager, ReportRepository reportRepository, ModelMapper modelMapper) {
        this.manager = manager;
        this.reportRepository = reportRepository;
        this.modelMapper = modelMapper;
    }

    public List<Report> findReports() {
        log.info("[reportService] selectReport  Start================================================");
        String jpql = "SELECT r " +
                "FROM tbl_report r " +
                "RIGHT JOIN r.productCode c ";
        List<Report> managment = manager.createQuery(jpql, Report.class).getResultList();

        log.info("[reportService] selectReport  END ================================================");
//        return reportRepository.findAll();
        return managment;
    }
//    (SELECT u.nickname FROM User u WHERE u.userCode =  r.product.refUserCode )

    @Transactional
    public String registReport(ReportDTO reportInfo) {
        log.info("[reportService] insertReport Start ==================================================");

        int result = 0; // 결과에 따른 값을 구분하기 위한 용도의 변수
        try {
            Report insertReport = modelMapper.map(reportInfo, Report.class);
            reportRepository.save(insertReport);
        } catch (Exception e) {
            System.out.println("check" + e);
            throw new RuntimeException(e);
        }
        log.info("[reportService] insertReport END ==================================================");
        return (result > 0) ? "신고하기 완료" : "신고하기 실패";
    }


    @Transactional
    public Object modifyReport(@RequestBody ReportDTO reportDTO) {
        log.info("[reportService] updateReport Start ================================================");

        int result = 0;

        try {
            Report report = reportRepository.findById(reportDTO.getReportNo()).get();
            log.info("Repository Check : " + report);

            report = report
                    .processDistinction(reportDTO.getProcessDistinction())
                    .processComment(reportDTO.getProcessComment())
                    .processDate(reportDTO.getProcessDate())
//                    .sellStatus(reportDTO.getSellStatusCode())
                    .build();
            result = 1;

        } catch (Exception e) {
            log.info("[Report update] Exception !!" + e);
        }
        log.info("[reportService] updateReport END ===============================================");
        return (result > 0) ? "처리 완료" : "처리 실패";
    }

    public List<Object[]> selectByProcessDetail() {
        String jpql = "SELECT r.productCode.productName, r.processDate , r.sellStatusCode.sellStatusCode,   r.reportComment, r.processComment " +
                "FROM tbl_report r " +
                "LEFT JOIN r.productCode c";
        List<Object[]> process = manager.createQuery(jpql).getResultList();

        return process;
    }


    public List<Object[]> selectByReportProduct() {
        String jpql = "SELECT r.refReportCategoryNo.reportCategoryCode, r.productCode.productName " +
                "FROM tbl_report r " +
                "LEFT JOIN r.refReportCategoryNo c";
        List<Object[]> categoryList = manager.createQuery(jpql).getResultList();

        return categoryList;
    }
    public List<Object[]> selectByReportProcess() {
        String jpql = "SELECT r.refReportCategoryNo.reportCategoryNo, r.productCode.productName, r.reportComment " +
                "FROM tbl_report r " +
                "LEFT JOIN r.productCode c";
        List<Object[]> reportList = manager.createQuery(jpql).getResultList();

        return reportList;
    }
    public List<Object[]> selectByReportManagement() {
        log.info("[reportService] selectReport Start ================================================");
        String jpql = "SELECT r.reportNo, (SELECT u.nickname FROM User u WHERE u.userCode =  r.productCode.refUserCode ), r.productCode.productName, r.sellStatusCode.sellStatus, r.refReportCategoryNo.reportCategoryCode " +
                "FROM tbl_report r " +
                "RIGHT JOIN r.productCode c";
        List<Object[]> managment = manager.createQuery(jpql).getResultList();

        log.info("[reportService] selectReport END ================================================");

        return managment;

    }
}