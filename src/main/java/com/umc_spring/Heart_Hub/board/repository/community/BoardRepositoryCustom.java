package com.umc_spring.Heart_Hub.board.repository.community;

import com.umc_spring.Heart_Hub.board.model.community.Board;

public interface BoardRepositoryCustom {
    void addLikeCount(Board board);

    void subLikeCount(Board board);
}
