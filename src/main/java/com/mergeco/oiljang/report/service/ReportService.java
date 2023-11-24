package com.mergeco.oiljang.report.service;

import com.mergeco.oiljang.common.paging.Criteria;
import com.mergeco.oiljang.product.entity.SellStatus;
import com.mergeco.oiljang.report.dto.ReportsDTO;
import com.mergeco.oiljang.report.entity.Report;
import com.mergeco.oiljang.report.dto.ReportDTO;
import com.mergeco.oiljang.report.repository.ReportRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.persistence.*;
import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

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

 /*   public List<Report> findReports() {
        log.info("[reportService] selectReport  Start==================================");
        String jpql = "SELECT r " +
                "FROM tbl_report r " +
                "RIGHT JOIN r.productCode c ";
        List<Report> managment = manager.createQuery(jpql, Report.class).getResultList();
        System.out.println("???? : "+ managment);

        log.info("[reportService] selectReport  END ====================================");
        return reportRepository.findAll();
        return managment;
    }
    (SELECT u.nickname FROM User u WHERE u.userCode =  r.product.refUserCode )*/

    public List<ReportsDTO> findReports() {
        log.info("[reportService] selectReport Start ===========================");
        String jpql = "SELECT new com.mergeco.oiljang.report.dto.ReportsDTO (r.reportNo, r.reportDate, r.refReportCategoryNo.reportCategoryCode, r.productCode.productName,r.processDate, r.sellStatusCode.sellStatus, r.reportComment, r.processComment,r.processDistinction,r.reportUserNick, (SELECT u.nickname FROM User u WHERE u.userCode =  r.productCode.refUserCode )) " +
                "FROM tbl_report r " +
                "JOIN r.productCode c " +
                "ORDER BY r.reportNo DESC";
        List<ReportsDTO> management = manager.createQuery(jpql).getResultList();

        System.out.println("서비스 매니저: " + management);

        log.info("[reportService] selectReport END =============================");

        return management;
    }


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
    public String modifyReport(@RequestBody ReportDTO reportDTO) {
        log.info("[reportService] updateReport Start =============================");

        int result = 0;

        try {
            Report report = reportRepository.findById(reportDTO.getReportNo()).orElseThrow(IllegalArgumentException::new);

            log.info("Repository Check : " + report);
            report = report
                    .processDistinction(reportDTO.getProcessDistinction())
                    .processComment(reportDTO.getProcessComment())
                    .processDate(reportDTO.getProcessDate())
                    .sellStatusCode(new SellStatus(reportDTO.getSellStatusCode(), null))
                    .build();
            result = 1;

        } catch (Exception e) {
            log.info("[Report update] Exception !!" + e);
        }
        log.info("[reportService] updateReport END ================================");
        return (result > 0) ? "처리 완료" : "처리 실패";
    }

    public Report selectByProcessDetail(int reportNo) {
        log.info("[ReportService] selectByProcessDetail Start ======================================");

        Report report = reportRepository.findById(reportNo).get();

        System.out.println(report);

        log.info("[ReportService] selectByProcessDetail END ======================================");


        return report;
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

    public List<Report> selectReportList(String search) {
        log.info("[ReportService] selectReportList Start =================");
        log.info("[ReportService] search : {}", search);

        List<Report> reportListWithSearchValue = reportRepository.findByReportUserNickContaining(search);

        System.out.println("서비스 값다아옴 : " + reportListWithSearchValue);

        log.info("[ReportService] selectReportList END ===================");

        return reportListWithSearchValue;
    }

    public int selectProejctTotal() {
        log.info("[ReportService] selectProejctTotal Start =====");

        /* 페이징 처리 결과를 Page 타입으로 반환 받는다.*/
        List<Report> reportList = reportRepository.findAll();
        log.info("[ReportService] ReportList.size : {}", reportList.size());
        log.info("[ReportService] selectProejctTotal END ======");

        return reportList.size();
    }

    public Page<ReportsDTO> selectReportListWithPaging(Criteria cri) {

        log.info("[ReportService] selectReportListWithPaging Start =====");

        int index = cri.getPageNum() - 1;
        int count = cri.getAmount();

        Pageable pageable = PageRequest.of(index, count, Sort.by("reportNo").descending());

      /*  Pageable paging = PageRequest.of(index, count, Sort.by("reportNo").descending());

        Page<ReportsDTO> result = reportRepository.customQueryForReports(paging);
        System.out.println("서비스에서 레포지토리 확인 : " +  result);

        List<ReportsDTO> reportDTOList = result.stream()
                .map(report -> modelMapper.map(report, ReportsDTO.class))
                .collect(Collectors.toList());

        for(int i = 0; i <reportDTOList.size(); i++) {
            reportDTOList.get(i);
        }*/
        String jpql = "SELECT new com.mergeco.oiljang.report.dto.ReportsDTO (r.reportNo, r.reportDate, r.refReportCategoryNo.reportCategoryCode, r.productCode.productName,r.processDate, r.sellStatusCode.sellStatus, r.reportComment, r.processComment,r.processDistinction,r.reportUserNick, (SELECT u.nickname FROM User u WHERE u.userCode =  r.productCode.refUserCode )) " +
                "FROM tbl_report r " +
                "JOIN r.productCode c " +
                "ORDER BY r.reportNo DESC";
        TypedQuery<ReportsDTO> query = manager.createQuery(jpql, ReportsDTO.class);
        List<ReportsDTO> management = query.getResultList();

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), management.size());
        Page<ReportsDTO> reportPage = new PageImpl<>(management.subList(start, end), pageable, management.size());

        log.info("[ReportService] selectReportListWithPaging End =====");
        return reportPage;
    }
}