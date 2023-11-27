package com.mergeco.oiljang.report.controller;

import com.mergeco.oiljang.common.paging.Criteria;
import com.mergeco.oiljang.common.paging.PageDTO;
import com.mergeco.oiljang.common.paging.PagingResponseDTO;
import com.mergeco.oiljang.common.restApi.LoginMessage;
import com.mergeco.oiljang.common.restApi.ResponseMessage;
import com.mergeco.oiljang.report.dto.ReportDTO;
import com.mergeco.oiljang.report.dto.ReportsDTO;
import com.mergeco.oiljang.report.entity.Report;
import com.mergeco.oiljang.report.service.ReportService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/reports")
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @ApiOperation(value = "신고관리", notes = "신고관리 페이지입니다.", tags = {"ReportController"})
    @GetMapping("/reports")
    public ResponseEntity<?> selectReportListWithPageing(
            @RequestParam(name = "offset", defaultValue = "1") String offset) {

        log.info("[ReportController] selectReportListWithPaging Start ====");
        log.info("[ReportController] selectReportListWithPaging offset : {} ", offset);

        int total = reportService.selectProejctTotal();

        Criteria cri = new Criteria(Integer.valueOf(offset), 10);

        PagingResponseDTO pagingResponseDTO = new PagingResponseDTO();
//         1. offset의 번호에 맞는 페이지에 뿌려줄 Report
        pagingResponseDTO.setData(reportService.selectReportListWithPaging(cri));
//         2. PageDTO : 화면에서 페이징 처리에 필요한 정보들
        pagingResponseDTO.setPageInfo(new PageDTO(cri, total));

        log.info("[ReportController] selectReportListWithPaging END =====");
        return ResponseEntity.ok().body(new LoginMessage(HttpStatus.OK, "조회 성공", pagingResponseDTO));
    }


    @ApiOperation(value = "신고하기", notes = "유저가 신고를 등록합니다.", tags = {"ReportController"})
    @PostMapping("/report")
    public ResponseEntity<ResponseMessage> registReport(@RequestBody ReportDTO report) {
        System.out.println("insertReport : " + report);

        reportService.registReport(report);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("report", report);

        ResponseMessage responseMessage = new ResponseMessage(200, "신고하기완료", responseMap);
        return new ResponseEntity<>(responseMessage, getHeaders(), HttpStatus.OK);
    }


    @ApiOperation(value = "신고처리", notes = "관리자가 신고내용을 처리합니다.", tags = {"ReportController"})
    @PutMapping("/process")
    public ResponseEntity<?> processModify(@RequestBody ReportDTO reportDTO) {

        log.info("DTO 받아 오나요 ? : " + reportDTO);

        return ResponseEntity.ok().body(new LoginMessage(HttpStatus.OK, "접수된 신고 처리 완료", reportService.modifyReport(reportDTO)));
    }

    @ApiOperation(value = "신고처리 상세정보", notes = "관리자가 처리 내용을 확인", tags = {"ReportController"})
    @GetMapping("/processDetail/{reportNo}")
    public ResponseEntity<?> processDetail(@PathVariable int reportNo) {

        return ResponseEntity.ok().body(new LoginMessage(HttpStatus.OK, "신고처리 상세정보 조회 성공", reportService.selectByProcessDetail(reportNo)));
    }

    @ApiOperation(value = "신고관리 검색", notes = "신고자 검색", tags = {"ReportController"})
    @GetMapping("/search")
    public ResponseEntity<?> selectSearchReportList(
            @RequestParam(name = "s", defaultValue = "all") String search) {
        log.info("[ReportController] searchSelectReports Start ======================");
        System.out.println("컨트롤 리퀘스트 파람 : '" + search);

        log.info("[ReportController] searchSelectReports END ======================");

        return ResponseEntity
                .ok()
                .body(new LoginMessage(HttpStatus.OK, "조회 성공", reportService.selectReportList(search)));
    }

 /*   @ApiOperation(value = "처리 미처리 검색", notes = "처리 또는 미처리 검색", tags = {"ReportController"})
    @GetMapping("/processd")
    public List<Report> getReports(@RequestParam(required = false) Boolean processed, @RequestParam(required = false) String search) {
        log.info("[ReportController Start ================]");

        boolean isProcessed = processed != null ? processed :true;

        List<Report> reports = reportService.getProcessd(isProcessed, search);

        log.info("[ReportController END ================]");

        return reports;
    }*/


    //헤더 값
    private HttpHeaders getHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        return httpHeaders;
    }

}
