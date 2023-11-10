package com.mergeco.oiljang.report.controller;

import com.mergeco.oiljang.common.restApi.ResponseMessage;
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

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@Api(tags = "신고")
@Slf4j
@RequestMapping("/reports")
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @ApiOperation(value = "신고관리")
    @GetMapping("/reportManagerment")
    public ResponseEntity<ResponseMessage> findReportManagement() {

        List<Object[]> management = reportService.selectByReportManagement();

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("management", management);

        ResponseMessage responseMessage = new ResponseMessage(200, "신고관리", responseMap);
        return new ResponseEntity<>(responseMessage, getHeaders(), HttpStatus.OK);
    }

/*    @ApiOperation(value = "신고하기")
    @GetMapping("/report")
    public ResponseEntity<?> registReport(@RequestBody ReportDTO report) {

        System.out.println("insertReport : " + report);
        Report report = reportService.insertReport();


    }*/


    @ApiOperation(value = "신고처리")
    @GetMapping("/process")
    public ResponseEntity<ResponseMessage> processModify() {
    /*    HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        */
        List<Object[]> process = reportService.selectByReportProcess();

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("process", process);

        ResponseMessage responseMessage = new ResponseMessage(200, "신고처리", responseMap);
        return new ResponseEntity<>(responseMessage, getHeaders(), HttpStatus.OK);
    }

    //헤더 값
    private HttpHeaders getHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        return httpHeaders;
    }

}
