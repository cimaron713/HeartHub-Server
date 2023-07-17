package com.umc_spring.Heart_Hub.board.repository.community;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.umc_spring.Heart_Hub.board.dto.community.CommentDto;
import com.umc_spring.Heart_Hub.board.model.community.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.umc_spring.Heart_Hub.board.dto.community.CommentDto.Response.convertCommentToDto;
import static com.umc_spring.Heart_Hub.board.model.community.QComment.comment;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom{
    private CommentRepository commentRepository;
    private final JPAQueryFactory queryFactory;
    @Override
    public List<CommentDto.Response> findByBoardId(Long id) {
        List<Comment> reply = queryFactory.selectFrom(comment)
                .leftJoin(comment.parent).fetchJoin()
                .where(comment.board.boardId.eq(id))
                .orderBy(comment.parent.commentId.asc().nullsFirst())
                .fetch();

        List<CommentDto.Response> commentResponseDTOList = new ArrayList<>();
        Map<Long, CommentDto.Response> commentDTOHashMap = new HashMap<>();

        reply.forEach(c -> {
            CommentDto.Response commentResponseDTO = convertCommentToDto(c);
            commentDTOHashMap.put(commentResponseDTO.getCommentId(), commentResponseDTO);
            if (c.getParent() != null) commentDTOHashMap.get(c.getParent().getCommentId()).getResponseList().add(commentResponseDTO);
            else commentResponseDTOList.add(commentResponseDTO);
        });
        return commentResponseDTOList;
    }
}
