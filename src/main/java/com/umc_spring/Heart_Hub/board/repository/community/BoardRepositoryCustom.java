package com.umc_spring.Heart_Hub.board.repository.community;

import com.umc_spring.Heart_Hub.board.model.community.Board;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface BoardRepositoryCustom {
    List<Board> boardInWeek(LocalDate startDate, LocalDate endDate);
}
