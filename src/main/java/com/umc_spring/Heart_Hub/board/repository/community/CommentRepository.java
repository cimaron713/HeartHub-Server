package com.umc_spring.Heart_Hub.board.repository.community;

import com.umc_spring.Heart_Hub.board.dto.community.BoardDto;
import com.umc_spring.Heart_Hub.board.dto.community.CommentDto;
import com.umc_spring.Heart_Hub.board.model.community.Board;
import com.umc_spring.Heart_Hub.board.model.community.Comment;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long>,CommentRepositoryCustom {
    List<Comment> findAllByBoard(Board board);

}
