package com.umc_spring.Heart_Hub.board.repository.community;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.umc_spring.Heart_Hub.board.model.community.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.umc_spring.Heart_Hub.board.model.community.QComment.comment;

@Repository
@RequiredArgsConstructor
public class CommentGoodRepositoryCustomImpl implements CommentGoodRepositoryCustom{
    private final JPAQueryFactory queryFactory;
    @Override
    public void addLikeCount(Comment selectComment) {
//        queryFactory.update(comment)
//                .set(comment..likeCount, comment.likeCount.add(1))
//                .where(comment.eq(selectComment))
//                .execute();
    }

    @Override
    public void subLikeCount(Comment comment) {

    }
}
