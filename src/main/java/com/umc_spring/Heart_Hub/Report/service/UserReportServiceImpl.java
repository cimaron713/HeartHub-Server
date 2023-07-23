package com.umc_spring.Heart_Hub.Report.service;

import com.umc_spring.Heart_Hub.Report.dto.ReportDto;
import com.umc_spring.Heart_Hub.Report.model.UserReport;
import com.umc_spring.Heart_Hub.Report.repository.UserReportRepository;
import com.umc_spring.Heart_Hub.board.service.community.BoardService;
import com.umc_spring.Heart_Hub.constant.enums.ErrorCode;
import com.umc_spring.Heart_Hub.constant.exception.CustomException;
import com.umc_spring.Heart_Hub.user.model.User;
import com.umc_spring.Heart_Hub.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserReportServiceImpl implements UserReportService{

    private final UserRepository userRepository;
    private final UserReportRepository userReportRepository;
    private final BoardService boardService;

    @Override
    public ReportDto.UserReportResDto reportUser(ReportDto.UserReportReqDto reqDto, String username) {
        User reporter = userRepository.findByUsername(username);
        User reported = userRepository.findByUsername(reqDto.getReportedUsername());

        if (reporter == null || reported == null) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        UserReport userReport;
        if (userReportRepository.findByReporterAndReported(reporter, reported) == null) {
            //신고한 적이 없을 경우
            userReport = new UserReport(reporter, reported, reqDto.getReason(), reqDto.getDetail());
            userReportRepository.save(userReport);
        } else {
            //이미 신고를 했을 경우
            throw new CustomException(ErrorCode.ALREADY_REPORTED);
        }

        List<UserReport> userReports = userReportRepository.findByReported(reported);
        if (userReports.size() >= 4) {
            reported.suspendUser();
        } else if (userReports.size() >= 3) {
            reported.deleteUserContents();
            boardService.delAllBoard(reported);
        } else if (userReports.size() >= 2) {
            reported.warnUser();
        }

        userRepository.save(reported);

        return new ReportDto.UserReportResDto(userReport);
    }
}
