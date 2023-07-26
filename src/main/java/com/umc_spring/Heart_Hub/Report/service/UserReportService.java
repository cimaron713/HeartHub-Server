package com.umc_spring.Heart_Hub.Report.service;

import com.umc_spring.Heart_Hub.Report.dto.ReportDto;

public interface UserReportService {

    ReportDto.UserReportResDto reportUser(ReportDto.UserReportReqDto reqDto, String username) throws Exception;
}
