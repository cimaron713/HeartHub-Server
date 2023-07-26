package com.umc_spring.Heart_Hub.Report.controller;

import com.umc_spring.Heart_Hub.Report.dto.ReportDto;
import com.umc_spring.Heart_Hub.Report.service.UserReportService;
import com.umc_spring.Heart_Hub.constant.dto.ApiResponse;
import com.umc_spring.Heart_Hub.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ReportController {

    private final UserService userService;
    private final UserReportService userReportService;

    @Autowired
    public ReportController(UserService userService, UserReportService userReportService) {
        this.userService = userService;
        this.userReportService = userReportService;
    }

    @PostMapping("/report/user")
    public ResponseEntity<ApiResponse<ReportDto.UserReportResDto>> userReport(@RequestBody ReportDto.UserReportReqDto reqDto, Authentication authentication) throws Exception {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        ReportDto.UserReportResDto resDto = userReportService.reportUser(reqDto, userDetails.getUsername());

        return ResponseEntity.ok().body(ApiResponse.createSuccess(resDto, "Report Success!"));
    }
}
