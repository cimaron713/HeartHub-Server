package com.umc_spring.Heart_Hub.board.repository.community;

import com.umc_spring.Heart_Hub.board.model.community.Comment;

public interface CommentGoodRepositoryCustom {
    void addLikeCount(Comment comment);

    void subLikeCount(Comment comment);
}
