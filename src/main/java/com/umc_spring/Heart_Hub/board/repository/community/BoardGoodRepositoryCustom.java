package com.umc_spring.Heart_Hub.board.repository.community;

import com.umc_spring.Heart_Hub.board.model.community.Board;

import java.util.List;

public interface BoardGoodRepositoryCustom {

    List<Board> findTop3ByBoard();
}
