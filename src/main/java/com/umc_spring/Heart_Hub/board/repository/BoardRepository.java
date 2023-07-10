package com.umc_spring.Heart_Hub.board.repository;

import com.umc_spring.Heart_Hub.board.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {

}