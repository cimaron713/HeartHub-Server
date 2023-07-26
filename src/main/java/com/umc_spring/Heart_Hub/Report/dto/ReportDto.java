package com.umc_spring.Heart_Hub.Report.dto;

import com.umc_spring.Heart_Hub.Report.model.UserReport;
import com.umc_spring.Heart_Hub.Report.model.enums.ReportReason;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ReportDto {

    @Getter
    @NoArgsConstructor
    public static class UserReportReqDto {
        private String reportedUsername;
        private String email;
        private ReportReason reason;
        private String detail; //기타 사유

    }

    @Getter
    @NoArgsConstructor
    public static class UserReportResDto {
        private String reportedUsername;
        private String email;
        private ReportReason reason;

        @Builder
        public UserReportResDto(UserReport userReport) {
            this.reportedUsername = userReport.getReported().getUsername();
            this.email = userReport.getReported().getEmail();
            this.reason = userReport.getReason();
        }
    }
}
