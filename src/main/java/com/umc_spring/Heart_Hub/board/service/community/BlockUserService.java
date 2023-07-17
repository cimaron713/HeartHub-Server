package com.umc_spring.Heart_Hub.board.service.community;

import com.umc_spring.Heart_Hub.board.dto.community.BoardDto;

public interface BlockUserService {

    void blockUser(String username, BoardDto.BlockUserReqDto blockReqDto);
    void unblockUser(String username, BoardDto.UnblockUserReqDto unblockReqDto);
}
