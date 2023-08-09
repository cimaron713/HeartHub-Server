package com.umc_spring.Heart_Hub.Report.service;

import com.umc_spring.Heart_Hub.Report.dto.ReportDto;
import com.umc_spring.Heart_Hub.Report.model.BoardReport;
import com.umc_spring.Heart_Hub.Report.repository.BoardReportRepository;
import com.umc_spring.Heart_Hub.board.model.community.Board;
import com.umc_spring.Heart_Hub.board.repository.community.BoardRepository;
import com.umc_spring.Heart_Hub.board.service.community.BoardService;
import com.umc_spring.Heart_Hub.constant.enums.CustomResponseStatus;
import com.umc_spring.Heart_Hub.constant.exception.CustomException;
import com.umc_spring.Heart_Hub.email.EmailService;
import com.umc_spring.Heart_Hub.user.model.User;
import com.umc_spring.Heart_Hub.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardReportServiceImpl implements BoardReportService{

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final BoardReportRepository boardReportRepository;
    private final EmailService emailService;

    @Override
    public ReportDto.BoardReportResDto reportUser(ReportDto.BoardReportReqDto reqDto, String username) throws Exception {
        User reporter = userRepository.findByUsername(username);
        Board reportedBoard = boardRepository.findByBoardId(reqDto.getReportedBoardId());

        if (reporter == null) {
            throw new CustomException(CustomResponseStatus.USER_NOT_FOUND);
        }

        if (reportedBoard == null) {
            throw new CustomException(CustomResponseStatus.POST_NOT_FOUND);
        }

        BoardReport boardReport;
        if (boardReportRepository.findByReporterAndReportedBoard(reporter, reportedBoard) == null) {
            //신고한 적이 없을 경우
            boardReport = new BoardReport(reporter, reportedBoard, reqDto.getReason(), reqDto.getDetail());
            boardReportRepository.save(boardReport);
        } else {
            //이미 신고를 했을 경우
            throw new CustomException(CustomResponseStatus.ALREADY_REPORTED);
        }

        List<BoardReport> boardReports = boardReportRepository.findByReportedBoard(reportedBoard);
        if (boardReports.size() >= 5) {  //계정 정지
            reportedBoard.getUser().suspendUser();
            emailService.sendSuspendMessage(reportedBoard.getUser().getEmail());
        } else if (boardReports.size() >= 3) {  //해당 게시물 삭제
            reportedBoard.getUser().deleteUserContents();
            boardRepository.deleteByBoardId(reportedBoard.getBoardId());
            emailService.sendContentDelMessage(reportedBoard.getUser().getEmail());
        } else if (boardReports.size() >= 1) {  //경고
            reportedBoard.getUser().warnUser();
            emailService.sendWarningMessage(reportedBoard.getUser().getEmail());
        }

        userRepository.save(reportedBoard.getUser());

        return new ReportDto.BoardReportResDto(boardReport);
    }
}
