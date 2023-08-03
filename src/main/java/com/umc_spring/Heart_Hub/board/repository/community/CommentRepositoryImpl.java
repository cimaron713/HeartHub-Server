package com.umc_spring.Heart_Hub.board.repository.community;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.umc_spring.Heart_Hub.board.dto.community.CommentDto;
import com.umc_spring.Heart_Hub.board.model.community.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import static com.umc_spring.Heart_Hub.board.dto.community.CommentDto.Response.convertCommentToDto;
import static com.umc_spring.Heart_Hub.board.model.community.QComment.comment;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom{
    private final JPAQueryFactory queryFactory;
    @Override
    public List<CommentDto.Response> findByBoardId(Long id) {

        List<Comment> comments = queryFactory.selectFrom(comment)
                .leftJoin(comment.parent).fetchJoin()
                .where(comment.board.boardId.eq(id))
                .orderBy(comment.parent.commentId.asc().nullsFirst(),
                        comment.count.desc())
                .fetch();

        List<CommentDto.Response> commentResponseDTOList = new ArrayList<>();
        Map<Long, CommentDto.Response> commentDTOHashMap = new HashMap<>();

        comments.forEach(c -> {
            CommentDto.Response commentResponseDTO = convertCommentToDto(c);
            commentDTOHashMap.put(commentResponseDTO.getCommentId(), commentResponseDTO);
            if (c.getParent() != null) commentDTOHashMap.get(c.getParent().getCommentId()).getResponseList().add(commentResponseDTO);
            else commentResponseDTOList.add(commentResponseDTO);
        });
        return commentResponseDTOList;
    }

    @Override
    public void addLikeCount(Comment selectComment) {
        queryFactory.update(comment)
                .set(comment.count, comment.count.add(1))
                .where(comment.eq(selectComment))
                .execute();
    }

    @Override
    public void subLikeCount(Comment subComment) {
        queryFactory.update(comment)
                .set(comment.count, comment.count.subtract(1))
                .where(comment.eq(subComment))
                .execute();
    }
}
