package com.umc_spring.Heart_Hub.board.repository.community;

import com.umc_spring.Heart_Hub.board.model.community.Board;
import com.umc_spring.Heart_Hub.user.model.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long>,BoardRepositoryCustom {
    List<Board> findAllByTheme(Sort sort, String theme);
    Optional<Board> findByBoardIdAndTheme(Long id, String theme);
    List<Board> findAllByUser(User user);
    List<Board> findAllByTheme(String theme);
    Board findByBoardId(Long boardId);
    void deleteByBoardId(Long boardId);
}
