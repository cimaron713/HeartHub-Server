package com.umc_spring.Heart_Hub.board.repository.community;

import com.umc_spring.Heart_Hub.board.model.community.Board;
import com.umc_spring.Heart_Hub.board.model.community.BoardGood;
import com.umc_spring.Heart_Hub.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BoardGoodRepository extends JpaRepository<BoardGood,Long> {
    Optional<BoardGood> findByUserAndBoard(User user, Board board);

    @Query(value = "select count(*) from BoardGood group by BOARD_ID > :id", nativeQuery = true)
    public List<BoardGood> countGood(@Param(value = "id") Long id);
}
