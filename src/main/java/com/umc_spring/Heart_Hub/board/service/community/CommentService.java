package com.umc_spring.Heart_Hub.board.service.community;

import com.umc_spring.Heart_Hub.board.dto.community.CommentDto;
import com.umc_spring.Heart_Hub.board.model.community.Board;
import com.umc_spring.Heart_Hub.board.model.community.Comment;
import com.umc_spring.Heart_Hub.board.repository.community.BoardRepository;
import com.umc_spring.Heart_Hub.board.repository.community.CommentRepository;
import com.umc_spring.Heart_Hub.user.model.User;
import com.umc_spring.Heart_Hub.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private CommentRepository commentRepository;
    private BoardRepository boardRepository;
    private UserRepository userRepository;

    /*
    해당 게시글 댓글 목록
     */
    @Transactional
    public List<CommentDto.Response> findComments(Long id){
        List<CommentDto.Response> comments = commentRepository.findAllByBoardId(id).stream().toList();
        return comments;
    }

    /*
    댓글 등록
     */
    @Transactional
    public Long createComment(Long id, CommentDto.Request commentRequest, String username){
        User user = userRepository.findByUsername(username);
        Board board = boardRepository.findById(id).orElseThrow();
        Comment newComment = Comment .builder()
                .content(commentRequest.getContent())
                .user(user)
                .board(board)
                .build();
        commentRepository.save(newComment);
        return newComment.getCommentId();
    }
}
