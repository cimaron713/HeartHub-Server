package com.umc_spring.Heart_Hub.board.service.community;

import com.umc_spring.Heart_Hub.board.dto.community.CommentDto;
import com.umc_spring.Heart_Hub.board.model.community.Comment;
import com.umc_spring.Heart_Hub.board.model.community.CommentGood;
import com.umc_spring.Heart_Hub.board.repository.community.CommentGoodRepository;
import com.umc_spring.Heart_Hub.board.repository.community.CommentRepository;
import com.umc_spring.Heart_Hub.constant.enums.CustomResponseStatus;
import com.umc_spring.Heart_Hub.constant.exception.CustomException;
import com.umc_spring.Heart_Hub.user.model.User;
import com.umc_spring.Heart_Hub.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentGoodService {
    private final CommentRepository commentRepository;
    private final CommentGoodRepository commentGoodRepository;
    private final UserRepository userRepository;

    /**
     * 댓글 좋아요
     */
    public void commentGood(Long id, String username){
        User user = userRepository.findByUsername(username);
        if(user == null) {
            throw new CustomException(CustomResponseStatus.USER_NOT_FOUND);
        }
        Comment comment= commentRepository.findById(id).orElseThrow(() -> {
            throw new CustomException(CustomResponseStatus.COMMENT_NOT_FOUND);
        });

        if(comment.getParent() != null){
            return;
        }
        if(commentGoodRepository.findByUserAndComment(user, comment).isEmpty()){
            //좋아요 안누른 유저만 좋아요
            CommentGood commentGood = CommentGood.builder()
                    .comment(comment)
                    .user(user)
                    .status("Y")
                    .build();
            commentGoodRepository.save(commentGood);
        }
        else{
            //좋아요 취소
            CommentGood commentGood = commentGoodRepository.findByUserAndComment(user,comment).orElseThrow();
            commentGoodRepository.delete(commentGood);
        }
    }

    public String commentGoodCnt(Long id){
        Comment comment = commentRepository.findById(id).orElseThrow();
        if(comment == null){
            return "0";
        }
        else{
            int goodCount = commentGoodRepository.findAllByComment(comment).size();
            String cnt = String.valueOf(goodCount);
            return cnt;
        }

    }
}
