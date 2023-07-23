package com.umc_spring.Heart_Hub.Report.model.enums;

public enum ReportReason {
    PROFANITY("욕설, 혐오 발언"),
    SPAM("광고성 스팸"),
    INAPPROPRIATE_CONTENT("부적절한 콘텐츠"),
    INCITING_CONFLICT("갈등조장 및 허위사실 유포"),
    OTHER("기타");

    private final String reason;

    ReportReason(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }
}
