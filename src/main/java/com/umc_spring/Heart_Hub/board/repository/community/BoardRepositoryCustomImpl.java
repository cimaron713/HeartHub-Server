package com.umc_spring.Heart_Hub.board.repository.community;

import com.querydsl.core.types.EntityPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.umc_spring.Heart_Hub.board.model.community.Board;
import com.umc_spring.Heart_Hub.board.model.community.QBoard;
import com.umc_spring.Heart_Hub.board.model.community.QComment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.umc_spring.Heart_Hub.board.model.community.QBoard.board;

@RequiredArgsConstructor
@Repository
public class BoardRepositoryCustomImpl implements BoardRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public void addLikeCount(Board selectBoard) {
//        jpaQueryFactory.update(board)
//                .set(board., board.getLikeCount().add.add(1))
//                .where(board.eq(selectedBoard))
//                .execute();
    }

    @Override
    public void subLikeCount(Board board) {

    }
}
