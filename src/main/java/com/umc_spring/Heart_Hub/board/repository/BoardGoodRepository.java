package com.umc_spring.Heart_Hub.board.repository;

import com.umc_spring.Heart_Hub.board.model.Board;
import com.umc_spring.Heart_Hub.board.model.BoardGood;
import com.umc_spring.Heart_Hub.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BoardGoodRepository extends JpaRepository<BoardGood,Long> {
    Optional<BoardGood> findByUserBoard(User user, Board board);

    @Query(value = "select count(*) from BoardGood Group By BOARD_ID > :id", nativeQuery = true)
    public List<BoardGood> countGood(@Param(value = "id") Long id);
}
