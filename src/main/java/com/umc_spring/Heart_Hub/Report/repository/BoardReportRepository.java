package com.umc_spring.Heart_Hub.Report.repository;

import com.umc_spring.Heart_Hub.Report.model.BoardReport;
import com.umc_spring.Heart_Hub.board.model.community.Board;
import com.umc_spring.Heart_Hub.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardReportRepository extends JpaRepository<BoardReport, Long> {

    BoardReport findByReporterAndReportedBoard(User reporter, Board reportedBoard);
    List<BoardReport> findByReportedBoard(Board reportedBoard);

}
