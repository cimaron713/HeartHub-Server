package com.umc_spring.Heart_Hub.Report.controller;

import com.umc_spring.Heart_Hub.Report.dto.ReportDto;
import com.umc_spring.Heart_Hub.Report.service.BoardReportService;
import com.umc_spring.Heart_Hub.constant.dto.ApiResponse;
import com.umc_spring.Heart_Hub.constant.enums.CustomResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class ReportController {

    private final BoardReportService boardReportService;

    @PostMapping("/report")
    public ResponseEntity<ApiResponse<ReportDto.BoardReportResDto>> userReport(@RequestBody ReportDto.BoardReportReqDto reqDto, Authentication authentication) throws Exception {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        ReportDto.BoardReportResDto resDto = boardReportService.reportUser(reqDto, userDetails.getUsername());

        return ResponseEntity.ok().body(ApiResponse.createSuccess(resDto, CustomResponseStatus.SUCCESS));
    }
}
