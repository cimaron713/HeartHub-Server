package com.umc_spring.Heart_Hub.Report.model.enums;

public enum ReportStatus {
    NORMAL("정상"),
    WARNED("경고"),
    CONTENT_DELETED("콘텐츠 삭제"),
    ACCOUNT_SUSPENDED("계정 정지");

    private final String status;

    ReportStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }
}
