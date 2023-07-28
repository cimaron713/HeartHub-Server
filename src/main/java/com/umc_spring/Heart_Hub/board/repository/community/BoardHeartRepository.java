package com.umc_spring.Heart_Hub.board.repository.community;

import com.umc_spring.Heart_Hub.board.model.community.Board;
import com.umc_spring.Heart_Hub.board.model.community.BoardGood;
import com.umc_spring.Heart_Hub.board.model.community.BoardHeart;
import com.umc_spring.Heart_Hub.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BoardHeartRepository extends JpaRepository<BoardHeart, Long> {
    Optional<BoardHeart> findByUserAndBoard(User user, Board board);
    List<BoardHeart> findAllByUserAndBoard_User(User user, User blockedUser);
    List<BoardHeart> findByUser(User user);

    @Query(value = "select count(*) from BoardHeart group by BOARD_ID > :id", nativeQuery = true)
    public List<BoardHeart> countHeart(@Param(value = "id") Long id);
}
