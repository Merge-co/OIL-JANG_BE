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
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
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
            @RequestParam(name = "offset", defaultValue = "1") String offset,
            @RequestParam(name = "s", defaultValue = "all") String search,
            @RequestParam(name = "p", defaultValue = "all") String processed) {

        log.info("[ReportController] selectReportListWithPagingAndSearch Start ========");
        log.info("[ReportController] selectReportListWithPagingAndSearch offset : {}", offset);
        log.info("[ReportController] selectReportListWithPagingAndSearch search : {}", search);
        log.info("[ReportController] selectReportListWithPagingAndSearch processed : {}", processed);

        try {
            Criteria cri = new Criteria(Integer.valueOf(offset), 10);
            PagingResponseDTO pagingResponseDTO = new PagingResponseDTO();

            if (!"all".equals(search)) {
                // 검색 기능이 활성화 된 경우
                int total = reportService.selectProejctTotal();
                pagingResponseDTO.setData(reportService.selectReportList(cri, search));
                pagingResponseDTO.setPageInfo(new PageDTO(cri, total));
            } else if (!"all".equals(processed)){
                    // 처리 미처리 기능이 활성화 된 경우
                int total = reportService.selectProejctTotal();
                pagingResponseDTO.setData(reportService.selectProcessed(cri, processed));
                pagingResponseDTO.setPageInfo(new PageDTO(cri, total));
            }else {
                //일반 페이징 조회
                int total = reportService.selectProejctTotal();
                pagingResponseDTO.setData(reportService.selectReportListWithPaging(cri));
                pagingResponseDTO.setPageInfo(new PageDTO(cri, total));
            }

            log.info("[ReportController] selectReportListWithPagingAndSearch END===========");

            return ResponseEntity.ok().body(new LoginMessage(HttpStatus.OK, "조회 성공", pagingResponseDTO));
        } catch (Exception e) {
            log.error("요청을 처리하는 동안 오휴 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new LoginMessage(HttpStatus.INTERNAL_SERVER_ERROR, "요청을 처리하는 동안 에러 발생", null));
        }
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

    @ApiOperation(value = "신고처리 정보조회", notes = "접수된 신고를 처리를 위한 정보조회", tags = {"ReportController"})
    @GetMapping("/processingDetail/{reportNo}")
    public ResponseEntity<?> processingDetail(@PathVariable int reportNo, @RequestParam(name = "user", defaultValue = "1") int userCode) {
        return ResponseEntity.ok().body(new LoginMessage(HttpStatus.OK, "신고처리를 위한 정보 조회", reportService.selectByProcessingDetail(reportNo, userCode)));
    }
    //헤더 값
    private HttpHeaders getHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        return httpHeaders;
    }
}
