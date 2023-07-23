package com.umc_spring.Heart_Hub.Report.model;

import com.umc_spring.Heart_Hub.constant.entity.BaseEntity;
import com.umc_spring.Heart_Hub.Report.model.enums.ReportReason;
import com.umc_spring.Heart_Hub.user.model.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserReport extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "reporter", nullable = false)
    private User reporter;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "reported", nullable = false)
    private User reported;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReportReason reason;

    @Column(length = 500)
    private String detail;  //기타 사유

    public UserReport(User reporter, User reported, ReportReason reason, String detail) {
        this.reporter = reporter;
        this.reported = reported;
        this.reason = reason;
        this.detail = detail;
    }
}
