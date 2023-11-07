package com.mergeco.oiljang.report.service;

import com.mergeco.oiljang.report.entity.Report;
import com.mergeco.oiljang.report.repository.ReportRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.text.BreakIterator;
import java.util.List;

@Service
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
        String jpql = "SELECT n.processDistincation FROM tbl_report as n WHERE n.reportNo = 1";
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
        String jpql = "SELECT m FROM tbl_report m WHERE m.processDistincation = ?1";
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
                .setParameter("categoryCode" , categoryCode)
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
        String jpql = "SELECT r.reportCategory.reportCategoryNo, r.product.productCode " +
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
        String jpql = "SELECT r.reportNo, r.product.refUserCode, r.product.productName, r.reportCategory.reportCategoryCode, r.sellStatus.sellStatus " +
                "FROM tbl_report r " +
                "RIGHT JOIN r.product c";
        List<Object[]> managment = manager.createQuery(jpql).getResultList();

        return managment;

    }

    public List<Object[]> selectByProcessDetail() {
        String jpql = "SELECT r.reportNo, r.reportDate, r.reportCategory.reportCategoryCode, r.product.productName, r.processDate, r.sellStatus.sellStatus, r.reportComment, r.processComment " +
                "FROM tbl_report r " +
                "LEFT JOIN r.product c";
        List<Object[]> process = manager.createQuery(jpql).getResultList();

        return process;
    }



   /* public List<Object[]> selectByCollectionJoun() {
        String jpql = "SELECT c.reportCategoryNo, p.productCode " +
                "FROM ReportCategory c " +
                "LEFT JOIN c.product p ";
        List<Object[]> caterogyList = manager.createQuery(jpql).getResultList();
        return caterogyList;
    }*/

}
