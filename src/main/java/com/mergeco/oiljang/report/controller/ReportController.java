package com.mergeco.oiljang.report.controller;

import com.mergeco.oiljang.common.restApi.ResponseMessage;
import com.mergeco.oiljang.report.model.dto.ReportDTO;
import com.mergeco.oiljang.report.service.ReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "신고")
@Slf4j
@RequestMapping("/report")
public class ReportController {

    private final ReportService reportService;


    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @ApiOperation(value = "신고하기")
    @GetMapping("/reports")
    public ResponseEntity<ResponseMessage> findReportProduct() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        List<Object[]> reportList = reportService.selectByReportProduct();

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("reportList", reportList);

        ResponseMessage responseMessage = new ResponseMessage(200, "신고하기", responseMap);
        return new ResponseEntity<>(responseMessage, headers, HttpStatus.OK);



    }
}
