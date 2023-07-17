package com.umc_spring.Heart_Hub.board.repository.community;

import com.umc_spring.Heart_Hub.board.dto.community.CommentDto;

import java.util.List;

public interface CommentRepositoryCustom {
    List<CommentDto.Response> findByBoardId(Long id);
}
