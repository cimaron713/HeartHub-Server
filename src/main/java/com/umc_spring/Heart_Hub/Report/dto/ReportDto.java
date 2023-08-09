package com.umc_spring.Heart_Hub.Report.dto;

import com.umc_spring.Heart_Hub.Report.model.BoardReport;
import com.umc_spring.Heart_Hub.Report.model.enums.ReportReason;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ReportDto {

    @Getter
    @NoArgsConstructor
    public static class BoardReportReqDto {
        private Long reportedBoardId;
        private ReportReason reason;
        private String detail; //기타 사유

    }

    @Getter
    @NoArgsConstructor
    public static class BoardReportResDto {
        private Long reportedBoardId;
        private String email;
        private ReportReason reason;

        @Builder
        public BoardReportResDto(BoardReport boardReport) {
            this.reportedBoardId = boardReport.getReportedBoard().getBoardId();
            this.email = boardReport.getReportedBoard().getUser().getEmail();
            this.reason = boardReport.getReason();
        }
    }
}
