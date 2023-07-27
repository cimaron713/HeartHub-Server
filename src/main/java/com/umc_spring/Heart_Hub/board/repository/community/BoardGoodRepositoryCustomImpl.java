package com.umc_spring.Heart_Hub.board.repository.community;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.umc_spring.Heart_Hub.board.model.community.Board;
import com.umc_spring.Heart_Hub.board.model.community.QBoard;
import com.umc_spring.Heart_Hub.board.model.community.QBoardGood;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardGoodRepositoryCustomImpl implements BoardGoodRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Board> findTop3ByBoard() {
        QBoard board = QBoard.board;
        QBoardGood boardGood = QBoardGood.boardGood;

        return jpaQueryFactory
                .selectFrom(board)
                .leftJoin(boardGood).on(board.boardId.eq(boardGood.board.boardId))
                .where(boardGood.status.eq("T"))
                .groupBy(board.boardId)
                .orderBy(boardGood.count().desc())
                .limit(3)
                .fetch();
    }
}
