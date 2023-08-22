package com.umc_spring.Heart_Hub.board.service.community;

import com.amazonaws.services.kms.model.NotFoundException;
import com.umc_spring.Heart_Hub.board.dto.community.CommentGoodDto;
import com.umc_spring.Heart_Hub.board.model.community.Comment;
import com.umc_spring.Heart_Hub.board.model.community.CommentGood;
import com.umc_spring.Heart_Hub.board.repository.community.CommentGoodRepository;
import com.umc_spring.Heart_Hub.board.repository.community.CommentRepository;
import com.umc_spring.Heart_Hub.constant.enums.CustomResponseStatus;
import com.umc_spring.Heart_Hub.constant.exception.CustomException;
import com.umc_spring.Heart_Hub.user.model.User;
import com.umc_spring.Heart_Hub.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
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
            throw new CustomException((CustomResponseStatus.COMMENT_REPLY_NOT_GOOD));
        }
        if(commentGoodRepository.findByUserAndComment(user, comment).isPresent()){
            //좋아요 안누른 유저만 좋아요하게 에러 처리
            throw new CustomException(CustomResponseStatus.ALREADY_GOOD);
        }
        CommentGood commentGood = CommentGood.builder()
                .comment(comment)
                .user(user)
                .status("T")
                .build();
        commentGoodRepository.save(commentGood);
        commentRepository.addLikeCount(comment);

    }
    public void commentGoodDelete(Long id, String userName) {

        User user = userRepository.findByUsername(userName);
        if(user == null) {
            throw new CustomException(CustomResponseStatus.USER_NOT_FOUND);
        }
        Comment comment= commentRepository.findById(id).orElseThrow(() -> {
            throw new CustomException(CustomResponseStatus.COMMENT_NOT_FOUND);
        });

        CommentGood commentGood = commentGoodRepository.findByUserAndComment(user, comment)
                .orElseThrow(() -> new NotFoundException("Could not found comment id"));

        commentGoodRepository.delete(commentGood);
        commentRepository.subLikeCount(comment);
    }

    public String commentGoodCnt(Long id){
        Comment comment = commentRepository.findById(id).orElseThrow();
        int goodCount = commentGoodRepository.findAllByComment(comment).size();
        String cnt = String.valueOf(goodCount);
        return cnt;

    }

    public CommentGoodDto.commentCheckResponse commentGoodCheck(Long commentId, String userName){
        User user = userRepository.findByUsername(userName);
        if(user == null) {
            throw new CustomException(CustomResponseStatus.USER_NOT_FOUND);
        }
        Comment comment= commentRepository.findById(commentId).orElseThrow(() -> {
            throw new CustomException(CustomResponseStatus.COMMENT_NOT_FOUND);
        });
        if(comment.getParent() != null){
            throw new CustomException((CustomResponseStatus.COMMENT_REPLY_NOT_GOOD));
        }
        CommentGoodDto.commentCheckResponse response;
        if(commentGoodRepository.findByUserAndComment(user, comment).isEmpty()){
            response = CommentGoodDto.commentCheckResponse.builder()
                    .status("F")
                    .build();
        }
        else{
            response = CommentGoodDto.commentCheckResponse.builder()
                    .status("T")
                    .build();
        }
        return response;
    }
}
