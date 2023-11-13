package com.mergeco.oiljang.report.controller;

import com.mergeco.oiljang.common.restApi.ResponseMessage;
import com.mergeco.oiljang.report.dto.ReportCategoryDTO;
import com.mergeco.oiljang.report.dto.ReportDTO;
import com.mergeco.oiljang.report.entity.Report;
import com.mergeco.oiljang.report.service.ReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    @GetMapping("/reportManagerment")
    public ResponseEntity<ResponseMessage> findReportManagement() {

        List<Object[]> management = reportService.selectByReportManagement();

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("management", management);

        ResponseMessage responseMessage = new ResponseMessage(200, "신고관리 조회", responseMap);
        return new ResponseEntity<>(responseMessage, getHeaders(), HttpStatus.OK);
    }

    @ApiOperation(value = "신고하기", notes = "유저가 신고를 등록합니다.", tags = {"ReportController"})
    @PostMapping("/report")
    public ResponseEntity<?> registReport(@RequestBody ReportDTO report) {

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

        reportService.modifyReport(reportDTO);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("reportDTO", reportDTO);
        log.info("변경된 DTO : " + reportDTO);

     ResponseMessage responseMessage = new ResponseMessage(200, "신고처리 완료", responseMap);
     return new ResponseEntity<>(responseMessage, getHeaders(),HttpStatus.OK);
    }


    @ApiOperation(value = "처리상세", notes = "관리자가 처리 내용을 확인", tags = {"ReportController"})
    @GetMapping("/processDetail")
    public ResponseEntity<ResponseMessage> processDetail() {

        List<Object[]> process = reportService.selectByProcessDetail();

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("process", process);

        ResponseMessage responseMessage = new ResponseMessage(200, "처리상세", responseMap);
        return new ResponseEntity<>(responseMessage, getHeaders(), HttpStatus.OK);
    }

    //헤더 값
    private HttpHeaders getHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        return httpHeaders;
    }



}
