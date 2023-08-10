package com.umc_spring.Heart_Hub.board.repository.community;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.umc_spring.Heart_Hub.board.model.community.Board;
import com.umc_spring.Heart_Hub.board.model.community.QBoard;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
@Repository
@RequiredArgsConstructor
public class BoardRepositoryCustomImpl implements BoardRepositoryCustom{
    private final JPAQueryFactory queryFactory;
    QBoard board = QBoard.board;
    @Override
    public List<Board> boardInWeek(LocalDate startDate, LocalDate endDate) {
        List<Board> result = queryFactory.selectFrom(board)
                .where(board.theme.eq("L"),board.createdDate.between(startDate,endDate))
                .fetch();
        return result;
    }
}
