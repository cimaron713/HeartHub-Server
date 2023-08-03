package com.umc_spring.Heart_Hub.board.repository.community;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.umc_spring.Heart_Hub.board.dto.community.CommentDto;
import com.umc_spring.Heart_Hub.board.model.community.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import java.util.*;
import static com.umc_spring.Heart_Hub.board.dto.community.CommentDto.Response.convertCommentToDto;
import static com.umc_spring.Heart_Hub.board.model.community.QComment.comment;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom{
    private CommentRepository commentRepository;
    private final JPAQueryFactory queryFactory;
    @Override
    public List<CommentDto.Response> findByBoardId(Long id) {
//        List<Comment> mainReply = queryFactory.selectFrom(comment)
//                .where(comment.parent.isNull())
//                .orderBy(comment.goods.size().desc())
//                .fetch();
//        List<Comment> reply = queryFactory.selectFrom(comment)
//                .leftJoin(comment.parent).fetchJoin()
//                .where(comment.board.boardId.eq(id))
//                .orderBy(comment.parent.commentId.asc().nullsFirst())
//                .fetch();

//        for (Comment c: mainReply) {
//            List<Comment> child = c.getChildComment();
//            child.sort((Comparator<? super Comment>) Sort.by(Sort.Direction.DESC,"createDate"));
//            c.updateChild(child);
//        }

        List<Comment> comments = queryFactory.selectFrom(comment)
                .leftJoin(comment.parent).fetchJoin()
                .where(comment.board.boardId.eq(id))
                .orderBy(comment.parent.commentId.asc().nullsFirst(),
                        comment.parent.goods.size().desc(),
                        comment.createdDate.asc())
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
}
