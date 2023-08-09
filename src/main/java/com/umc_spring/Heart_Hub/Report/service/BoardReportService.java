package com.umc_spring.Heart_Hub.Report.service;


import com.umc_spring.Heart_Hub.Report.dto.ReportDto;

public interface BoardReportService {

    public ReportDto.BoardReportResDto reportUser(ReportDto.BoardReportReqDto reqDto, String username) throws Exception;
}
