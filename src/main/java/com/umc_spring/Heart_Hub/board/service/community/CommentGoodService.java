package com.umc_spring.Heart_Hub.board.service.community;

import com.umc_spring.Heart_Hub.board.dto.community.CommentDto;
import com.umc_spring.Heart_Hub.board.dto.community.CommentGoodDto;
import com.umc_spring.Heart_Hub.board.model.community.Comment;
import com.umc_spring.Heart_Hub.board.model.community.CommentGood;
import com.umc_spring.Heart_Hub.board.repository.community.CommentGoodRepository;
import com.umc_spring.Heart_Hub.board.repository.community.CommentRepository;
import com.umc_spring.Heart_Hub.user.model.User;
import com.umc_spring.Heart_Hub.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class CommentGoodService {
    private CommentRepository commentRepository;
    private CommentGoodRepository commentGoodRepository;
    private UserRepository userRepository;

    /**
     * 댓글 좋아요
     */
    public void commentGood(Long id,CommentDto.Request request, String username){
        User user = userRepository.findByUsername(username);
        Comment comment= commentRepository.findById(id).orElseThrow();

        if(commentGoodRepository.findByUserAndComment(user, comment) == null){
            //좋아요 안누른 유저만 좋아요
            CommentGood commentGood = CommentGood.builder()
                    .comment(comment)
                    .user(user)
                    .build();
            commentGoodRepository.save(commentGood);
        }
        else{
            //좋아요 취소
            CommentGood commentGood = commentGoodRepository.findByUserAndComment(user,comment).orElseThrow();
            commentGoodRepository.delete(commentGood);
        }
    }

    public int commentGoodCnt(Long id){
        int goodCount = commentGoodRepository.commentGoodCount(id).size();
        return goodCount;
    }
}
