package com.umc_spring.Heart_Hub.Report.model;

import com.umc_spring.Heart_Hub.Report.model.enums.ReportReason;
import com.umc_spring.Heart_Hub.board.model.community.Board;
import com.umc_spring.Heart_Hub.constant.entity.BaseEntity;
import com.umc_spring.Heart_Hub.user.model.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardReport extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "reporter", nullable = false)
    private User reporter;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "reportedBoard", nullable = false)
    private Board reportedBoard;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReportReason reason;

    @Column(length = 500)
    private String detail;  //기타 사유

    public BoardReport(User reporter, Board reportedBoard, ReportReason reason, String detail) {
        this.reporter = reporter;
        this.reportedBoard = reportedBoard;
        this.reason = reason;
        this.detail = detail;
    }
}
