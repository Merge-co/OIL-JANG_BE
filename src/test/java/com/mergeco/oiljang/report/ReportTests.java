package com.mergeco.oiljang.report;

import com.mergeco.oiljang.product.entity.SellStatus;
import com.mergeco.oiljang.report.dto.ReportCategoryDTO;
import com.mergeco.oiljang.report.dto.ReportDTO;
import com.mergeco.oiljang.report.repository.ReportRepository;
import com.mergeco.oiljang.report.service.ReportService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import com.mergeco.oiljang.report.entity.Report;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@SpringBootTest

public class ReportTests {



    private ReportRepository repository;
    @Autowired
    private ReportService service;

    @Autowired
    public ReportTests(ReportRepository repository) {
        this.repository = repository;
    }

    @DisplayName("Proxy 주소 값 조회")
    @Test
    public void proxyTest() {

        //given
        //when
        System.out.println(repository.getClass().getName());
        //then
    }

    @DisplayName("서비스 테스트")
    @Test
    void testService() {

        //given
        //when
        //then
        Assertions.assertNotNull(service);
        System.out.println("==============================");
        System.out.println("서비스 테스트 : ?" + service);
    }

    @DisplayName("신고분류, 상품정보 조회")
    @Test
    public void reportCategoryProductJoin() {
        //given
        //when
        List<Object[]> categoryList = service.selectByReportProduct();

        //then
        Assertions.assertNotNull(categoryList);
        categoryList.forEach(row -> {
            for (Object col : row) {
                System.out.print(col + " ");
            }
            System.out.println();
        });
    }

    @DisplayName("신고처리 : 신고분류, 판매게시글, 신고사유 조회")
    @Test
    public void reportProcess() {
        //given
        //when
        List<Object[]> reportList = service.selectByReportProcess();

        //then
        Assertions.assertNotNull(reportList);
        reportList.forEach(row -> {
            for (Object col : row) {
                System.out.print(col + " ");
            }
            System.out.println();
        });
    }

    @DisplayName("신고 관리페이지 - 신고번호, 판매자ID(닉네임 X), 판매글, 신고분류, 처리완료 조회")
    @Test
    public void reportManagementPage() {
        //given
        //when
        List<Object[]> management = service.selectByReportManagement();
        //then
        Assertions.assertNotNull(management);
        System.out.println("=======================================");
        management.forEach(row -> {
            for (Object col : row) {
                System.out.print(col + " : ");
            }
            System.out.println();
        });
    }

    @DisplayName("신고처리상세 - 신고번호, 접수일시, 신고분류, 판매글, 처리일시, 신고처리 결과, 신고사유, 신고처리내용 조회")
    @Test
    public void processDetail(int reportNo) {
        //given
        //when
        Report processDetail = service.selectByProcessDetail(reportNo);

        //then
        Assertions.assertNotNull(processDetail);
        System.out.println(processDetail);
//        System.out.println("================================");
//        processDetail.forEach(row -> {
//            for (Object col : row) {
//                System.out.print(col + " : ");
//            }
//            System.out.println();
//        });
    }

    /* ==================================================================================== */


    private static Stream<Arguments> getReportInfos() {
        return Stream.of(
                Arguments.of(
                        1, // 신고번호
                        "윤진쌤", // 신고자
                        "퇴실찍으세요 여러분 ~~", // 신고 내용
                        LocalDateTime.now(), // 신고 일시
                        1, //상품코드
                        1, //판매상태코드
                        1,  // 분류 코드
                        "광고성 게시글" // 신고분류
                )
        );
    }

    @DisplayName("신고하기 insert")
    @ParameterizedTest
    @MethodSource("getReportInfos")
    void reportInsertTest(
            int reportNo,
            String reportUserNick, // 신고자
            String reportComment, // 신고내용
            LocalDateTime reportDate, // 신고일시
            int product, //상품코드
            int sellStatusCode, //판매상태코드
            int refReportCategoryNo // 신고분류
    ) {
        //given
        ReportDTO reportInsert = new ReportDTO(
                reportNo,
                reportUserNick,
                reportComment,
                reportDate,
                product,
                sellStatusCode,
                refReportCategoryNo,
                null,
                null,
                null
        );
        //when
        System.out.println("===========================================");
        System.out.println("담겼냐 ? ; " + reportInsert);
        //then
        Assertions.assertDoesNotThrow(
                () -> service.registReport(reportInsert)
        );
    }

    @DisplayName("처리하기 update")
    @Test
    @Transactional
    void processInsertTest() {
        //given
        Optional<Report> report = repository.findById(1);
        //when
        report.ifPresent(modify -> {
            modify.processDistinction("수정하기 테스트");
            modify.processComment("수정하기 테스트 comment입니다.");
            modify.processDate(LocalDateTime.now());
            repository.save(modify);
        });
        //then
    }
}
