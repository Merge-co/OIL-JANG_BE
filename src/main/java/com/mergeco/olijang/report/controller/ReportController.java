package com.mergeco.olijang.report.controller;

import com.mergeco.olijang.report.model.dto.ReportDTO;
import com.mergeco.olijang.report.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/report")
    public String findReportByNo(int reportNo) {
        ReportDTO report = reportService.findReportByNo(reportNo);
        return null;
    }

}
