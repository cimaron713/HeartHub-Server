package com.umc_spring.Heart_Hub.board.repository.community;

import com.umc_spring.Heart_Hub.board.dto.community.CommentDto;
import com.umc_spring.Heart_Hub.board.model.community.Comment;

import java.util.List;

public interface CommentRepositoryCustom {
    List<CommentDto.Response> findByBoardId(Long id);
    void addLikeCount(Comment comment);

    void subLikeCount(Comment comment);
}
