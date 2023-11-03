package com.mergeco.olijang.report.service;

import com.mergeco.olijang.report.entity.Report;
import com.mergeco.olijang.report.model.dto.ReportDTO;
import com.mergeco.olijang.report.repository.ReportRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

    private final ReportRepository reportRepository ;
    private final ModelMapper modelMapper;

    public ReportService (ReportRepository reportRepository , ModelMapper modelMapper) {
        this.reportRepository = reportRepository;
        this.modelMapper = modelMapper;
    }

    public ReportDTO findReportByNo(int reportNo) {
        Report report = reportRepository.findById(reportNo).orElseThrow(IllegalAccessError::new);

        return modelMapper.map(report, ReportDTO.class);
    }

}
