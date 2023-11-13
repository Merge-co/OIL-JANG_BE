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
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ReportService(EntityManager manager, ReportRepository reportRepository, ProductRepository productRepository, ModelMapper modelMapper) {
        this.manager = manager;
        this.reportRepository = reportRepository;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    public Report findReport(int reportNo) {
        return manager.find(Report.class, reportNo);
    }

    public String selectSingleReportByTypedQuery() {
        String jpql = "SELECT n.reportUserNick FROM tbl_report as n WHERE n.reportNo = 1";
        TypedQuery<String> query = manager.createQuery(jpql, String.class);
        String resultNickName = query.getSingleResult();

        return resultNickName;
    }


    public Object selectSingleDistincationQuery() {
        String jpql = "SELECT n.processDistinction FROM tbl_report as n WHERE n.reportNo = 1";
        Query query = manager.createQuery(jpql);
        Object result = query.getSingleResult();

        return result;

    }

    public Report selectSingleRowByTypeQuery() {
        String jpql = "SELECT n FROM tbl_report as n WHERE n.reportCategory.reportCategoryNo = 1";
        TypedQuery<Report> query = manager.createQuery(jpql, Report.class);
        Report foundReport = query.getSingleResult();

        return foundReport;
    }

    public List<Report> selectMultipleRowQuery() {
        String jpql = "SELECT m FROM tbl_report as m";
        TypedQuery<Report> query = manager.createQuery(jpql, Report.class);
        List<Report> foundReportList = query.getResultList();
        return foundReportList;
    }

    public List<String> selectUsingDistinct() {
        String jpql = "SELECT DISTINCT m.reportCategory.reportCategoryCode FROM tbl_report m";
        TypedQuery<String> query = manager.createQuery(jpql, String.class);
        List<String> categoryCode = query.getResultList();

        return categoryCode;
    }

    public List<Report> selectUsingIn() {
        String jpql = "SELECT m FROM tbl_report m WHERE m.reportCategory.reportCategoryCode IN (1, 2, 3)";
        List<Report> foundReportList = manager.createQuery(jpql, Report.class).getResultList();

        return foundReportList;
    }

    public List<Report> selectUsingLike() {
        String jpql = "SELECT m FROM tbl_report m WHERE m.reportUserNick LIKE '%강%'";
        List<Report> foundReportList = manager.createQuery(jpql, Report.class).getResultList();
        return foundReportList;
    }

    public List<Report> selectReportByBindingName(String nickName) {
        String jpql = "SELECT m FROM tbl_report m WHERE m.reportUserNick = :reportUserNick";
        List<Report> foundReportList = manager.createQuery(jpql, Report.class)
                .setParameter("reportUserNick", nickName)
                .getResultList();

        return foundReportList;
    }

    public List<Report> selectReportByBindingPosition(String distincation) {
        String jpql = "SELECT m FROM tbl_report m WHERE m.processDistinction = ?1";
        List<Report> foundReportList = manager.createQuery(jpql, Report.class)
                .setParameter(1, distincation)
                .getResultList();
        return foundReportList;
    }

    public List<Report> usingPagingAPI(int offset, int limit) {
        String jpql = "SELECT m FROM tbl_report m ORDER BY m.reportNo DESC";

        List<Report> reportList = manager.createQuery(jpql, Report.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
        return reportList;
    }

    public Long countReportOfCategory(int categoryCode) {
        String jpql = "SELECT COUNT(m.reportCategory.reportCategoryNo) " +
                "FROM tbl_report m " +
                "WHERE m.reportCategory.reportCategoryNo = :categoryCode";

        Long countOfReport = manager.createQuery(jpql, Long.class)
                .setParameter("categoryCode", categoryCode)
                .getSingleResult();
        return countOfReport;
    }

    public List<Report> selectByInnerJoin() {
        String jpql = "SELECT m FROM tbl_report m JOIN m.product c";
        List<Report> reportList = manager.createQuery(jpql, Report.class).getResultList();

        return reportList;
    }


    public List<Object[]> selectByOuterJoin() {
        String jpql = "SELECT m.reportUserNick, c.reportCategoryCode " +
                "FROM tbl_report m " +
                "RIGHT JOIN m.reportCategory c " +
                "ORDER BY m.reportCategory.reportCategoryCode";
        List<Object[]> reportList = manager.createQuery(jpql).getResultList();
        return reportList;
    }

    public List<Object[]> selectByReportProduct() {
        String jpql = "SELECT r.reportCategory.reportCategoryCode, r.product.productName " +
                "FROM tbl_report r " +
                "LEFT JOIN r.reportCategory c";
        List<Object[]> categoryList = manager.createQuery(jpql).getResultList();

        return categoryList;
    }

    public List<Object[]> selectByReportProcess() {
        String jpql = "SELECT r.reportCategory.reportCategoryNo, r.product.productName, r.reportComment " +
                "FROM tbl_report r " +
                "LEFT JOIN r.product c";
        List<Object[]> reportList = manager.createQuery(jpql).getResultList();

        return reportList;
    }

    public List<Object[]> selectByReportManagement() {
        log.info("[reportService] selectReport Start ================================================");
        String jpql = "SELECT r.reportNo, (SELECT u.nickname FROM User u WHERE u.userCode =  r.product.refUserCode ), r.product.productName, r.sellStatus.sellStatus, r.reportCategory.reportCategoryCode " +
                "FROM tbl_report r " +
                "RIGHT JOIN r.product c";
        List<Object[]> managment = manager.createQuery(jpql).getResultList();

        log.info("[reportService] selectReport END ================================================");

        return managment;

    }

    public List<Object[]> selectByProcessDetail() {
        String jpql = "SELECT r.reportCategory.reportCategoryCode, r.product.productName, r.processDate , r.sellStatus.sellStatusCode,   r.reportComment, r.processComment " +
                "FROM tbl_report r " +
                "LEFT JOIN r.product c";
        List<Object[]> process = manager.createQuery(jpql).getResultList();

        return process;
    }

    public List<Report> selectWithSubQuery(String categoryName) {

        String jpql = "SELECT m.product.SellStatus.sellStatusCode " +
                "FROM tbl_report m " +
                "WHERE m.product.SellStatus.sellStatusCode " +
                "   =(SELECT c.sellStatusCode " +
                "       FROM SellStatus c " +
                "       WHERE c.sellStatusCode = :categoryName)";

        List<Report> reportList = manager.createQuery(jpql, Report.class)
                .setParameter("categoryName", categoryName)
                .getResultList();

        return reportList;
    }

    @Transactional
    public void registReport(ReportDTO reportInfo) {
        log.info("[reportService] insertReport Start ==================================================");

        reportRepository.save(modelMapper.map(reportInfo, Report.class));

        log.info("[reportService] insertReport END ==================================================");
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
                    .sellStatus(reportDTO.getSellStatus())
                    .build();

            result = 1;

        } catch (Exception e) {
            log.info("[Report update] Exception !!" + e);
        }

        log.info("[reportService] updateReport END ===============================================");
        return (result > 0) ? "처리 완료" : "처리 실패";
    }

  /*  @Transactional
    public ResponseEntity<ResponseMessage> insertReport(@PathVariable int productCode) {
        log.info("[ReportService] registReport Start ==============================");
        log.info("[ReportService] ReportDTO : " + productCode);


        //신고자 가져오기;
        ProductDTO productDTO = new ProductDTO();

        //insert
        ReportDTO reportDTO = new ReportDTO();

        String reportComment = "";

    }*/


}