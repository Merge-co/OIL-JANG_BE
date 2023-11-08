package com.mergeco.oiljang.report;

import com.mergeco.oiljang.product.dto.ProductDTO;
import com.mergeco.oiljang.product.dto.SellStatusDTO;
import com.mergeco.oiljang.report.model.dto.ReportCategoryDTO;
import com.mergeco.oiljang.report.model.dto.ReportDTO;
import com.mergeco.oiljang.report.repository.ReportRepository;
import com.mergeco.oiljang.report.service.ReportService;
import lombok.ToString;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import com.mergeco.oiljang.report.entity.Report;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@SpringBootTest

public class ReportTests {


    @Autowired
    private ReportRepository repository;
    @Autowired
    private ReportService service;

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

/*    @DisplayName("N:1 연관 관계 객체 그래프 탐색을 이용한 조회 테스트")
    @Test
    void manyToOneFindTest() {
        //given
        //when
        Report foundReport = service.findReport(1);
        ReportCategory category = foundReport.getReportCategory();
        System.out.println("========================= : "+foundReport);
        System.out.println("카테고리 ? : " + category );
        //then
        Assertions.assertNotNull(category);
    }*/

    @DisplayName("TypedQuery를 이용한 단일 조회테스트")
    @Test
    public void testSelectDingleReportNo() { // 이름만 조회 해봅시다.
        //given
        //when
        String nickName = service.selectSingleReportByTypedQuery();
        //then
        System.out.println(nickName);
        Assertions.assertEquals("강한성", nickName);
    }

    @DisplayName("Query를 이용한 단일 메뉴 조회 테스트")
    @Test
    public void testSelectSingleReportByQuery() { // 처리 분류 만 조회 해보겠습니다.
        //given
        //when
        Object distincation = service.selectSingleDistincationQuery();

        System.out.println("처리분류의 따른 조회  : " + distincation);
        //then
        Assertions.assertTrue(distincation instanceof String);
        Assertions.assertEquals("게시글삭제", distincation);
    }

    @DisplayName("TypedQuery를 이용한 단일행 조회 테스트")
    @Test
    public void testSelectSingleRow() {
        //given
        //when
        Report report = service.selectSingleRowByTypeQuery();
        //then
        Assertions.assertEquals(1, report.getReportNo());
        System.out.println(report);

    }

    @DisplayName("TypedQuery를 이용한 다중행 조회 테스트")
    @Test
    public void testSelectMultipleRowQuery() {
        //given
        //when
        List<Report> reportList = service.selectMultipleRowQuery();
        //then
        Assertions.assertNotNull(reportList);
        reportList.forEach(System.out::println);
    }

    @DisplayName("DISTINCT를 활용한 중복제거 조회")
    @Test
    public void testSelectUsingDistinct() {
        //given
        //when
        List<String> categoryNoList = service.selectUsingDistinct(); // code 타입이 int 가 아닌 String 이기 때문에 타입 조심
        //then
        Assertions.assertNotNull(categoryNoList);
        categoryNoList.forEach(System.out::println); // 결과 출력 해보기
    }

    @DisplayName("IN 연산자를 활용한 조회")
    @Test
    public void testSelectUsingIn() {
        //given
        //when
        List<Report> reportList = service.selectUsingIn();
        //then
        Assertions.assertNotNull(reportList);
        System.out.println("=-================================");
        System.out.println("in연산자를 활용한 조회 : " + reportList);
    }

    @DisplayName("LIKE 연산자를 활용한 조회")
    @Test
    public void testSelectUsingLike() {
        //given

        //when
        List<Report> reportList = service.selectUsingLike();
        //then
        Assertions.assertNotNull(reportList);
        reportList.forEach(System.out::println);
    }

    @DisplayName("이름 기준 파라미터 바인딩 목록조회")
    @Test
    public void testParameterBindingByName() {
        //given
        String nickName = "강한성";
        //when
        List<Report> reportList = service.selectReportByBindingName(nickName);
        //then
        Assertions.assertEquals(nickName, reportList.get(0).getReportUserNick());
        System.out.println("===============================================");
        System.out.println("이름 기준 파라미터 : " + reportList);
    }

    @DisplayName("위치 기준 파라미터 바인딩 목록 조회")
    @Test
    public void testParameterBindingByPosition() {
        //given
        String distincation = "게시글 삭제";
        //when
        List<Report> reportList = service.selectReportByBindingPosition(distincation);
        //then
        Assertions.assertEquals(distincation, reportList.get(0).getProcessDistincation());
        System.out.println("==========================================================");
        reportList.forEach(System.out::println);
    }

    @DisplayName("페이징 API를 이용한 조회 ")
    @Test
    public void testUsingPagingAPI() {

        //given
        int offset = 0; // 조회를 건널뛸 행
        int limit = 5; // 조회할 최대 항 수
        //when
        List<Report> reportList = service.usingPagingAPI(offset, limit);
        //then
        Assertions.assertNotNull(reportList);
        System.out.println("=============================================");
        reportList.forEach(System.out::println);
    }

    @DisplayName("특정 카테고리에 등록된 메뉴 수 조회")
    @Test
    public void testCountReportOfCategory() {
        //given
        int categoryCode = 3;
        //when
        Long countOfReport = service.countReportOfCategory(categoryCode);
        //then
        Assertions.assertTrue(countOfReport >= 0);
        System.out.println("countOfReport : " + countOfReport);
    }

    @DisplayName("내부조인을 이용한 조회")
    @Test
    public void testSelectByInnerJoin() {

        //given
        //when
        List<Report> reportList = service.selectByInnerJoin();
        //then
        Assertions.assertNotNull(reportList);
        System.out.println(reportList);
        System.out.println(" ===============================================");
        Assertions.assertTrue(reportList.size() > 0);
    }

    @DisplayName("외부조인을 이용한 조회")
    @Test
    public void testSelectByOuterJoin() {
        //given
        //when
        List<Object[]> reportList = service.selectByOuterJoin();
        //then
        Assertions.assertNotNull(reportList);
        reportList.forEach(row -> {
            for (Object col : row) {
                System.out.print(col + " ");
            }
            System.out.println();
        });
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
    public void processDetail() {
        //given
        //when
        List<Object[]> processDetail = service.selectByProcessDetail();

        //then
        Assertions.assertNotNull(processDetail);
        System.out.println(processDetail);
        System.out.println("================================");
        processDetail.forEach(row -> {
            for (Object col : row) {
                System.out.print(col + " : ");
            }
            System.out.println();
        });
    }

    @DisplayName("처리 미처리 선택 조회")
    @Test
    public void selectWithSubQuery() { // 미완료상태
        //given
        String categoryName = "삭제";
        //when
        List<Report> reportList = service.selectWithSubQuery(categoryName);
        //then
        Assertions.assertNotNull(reportList);
        reportList.forEach(System.out::println);
    }


/*    @DisplayName("신고하기 insert")
    @ParameterizedTest
    void reportInset () {

        //given
        ReportDTO report = new ReportDTO();
        report.setReportUserNick("윤진쌤");
        report.setReportComment("퇴실 찍으세요 여러분 ~~~/");
        report.setReportDate(LocalDateTime.now());
        report.setProductName("상품이름");
        report.setSellStateus("판매중");
        report.setReportCategoryNo(1);
        System.out.println("신고하기 테스트 : " + report);
        //when
        //then
        Assertions.assertDoesNotThrow(
                () -> service.registReport(report)
        );
    }*/









        /* ==================================================================================== */


    private static Stream<Arguments> getReportInfos() {
        return Stream.of(
                Arguments.of(
                        "윤진쌤",
                        "퇴실찍으세요 여러분 ~~",
                        LocalDateTime.now(),
                        "판매게시글",
                        "판매중(판매상태)",
                        1
                )
        );
    }

    @DisplayName("신고하기 insert")
    @ParameterizedTest
    @MethodSource("getReportInfos")
    void reportInsertTest(
            String reportUserNick,
            String reportComment,
            LocalDateTime reportDate,
            String productName,
            String sellStatus,
            int reportCategoryNo) {
        //given
        ReportDTO reportInsert = new ReportDTO(
                reportUserNick,
                reportComment,
                reportDate,
                productName,
                sellStatus,
                reportCategoryNo
        );
        //when
        System.out.println("===========================================");
        System.out.println(reportUserNick);
        System.out.println("방금이랑 똑같이 작성함 담겼냐 ? ; " + reportInsert);
        //then
        Assertions.assertDoesNotThrow(
                () -> service.registReport(reportInsert)
        );

    }

}
