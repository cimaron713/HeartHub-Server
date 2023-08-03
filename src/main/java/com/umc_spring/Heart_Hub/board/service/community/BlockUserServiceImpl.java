package com.umc_spring.Heart_Hub.board.service.community;

import com.umc_spring.Heart_Hub.board.dto.community.BoardDto;
import com.umc_spring.Heart_Hub.board.model.community.BlockedList;
import com.umc_spring.Heart_Hub.board.model.community.BoardGood;
import com.umc_spring.Heart_Hub.board.model.community.BoardHeart;
import com.umc_spring.Heart_Hub.board.repository.community.BlockedListRepository;
import com.umc_spring.Heart_Hub.board.repository.community.BoardGoodRepository;
import com.umc_spring.Heart_Hub.board.repository.community.BoardHeartRepository;
import com.umc_spring.Heart_Hub.constant.enums.CustomResponseStatus;
import com.umc_spring.Heart_Hub.constant.exception.CustomException;
import com.umc_spring.Heart_Hub.user.model.User;
import com.umc_spring.Heart_Hub.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class BlockUserServiceImpl implements BlockUserService{

    private final UserRepository userRepository;
    private final BlockedListRepository blockedListRepository;
    private final BoardHeartRepository boardHeartRepository;
    private final BoardGoodRepository boardGoodRepository;

    public void blockUser(String username, BoardDto.BlockUserReqDto blockReqDto) {
        User blocker = userRepository.findByUsername(username);
        User blockedUser = userRepository.findByUsername(blockReqDto.getBlockedUserName());
        if(blocker == null || blockedUser == null) {
            throw new CustomException(CustomResponseStatus.USER_NOT_FOUND);
        }

        if(blockedListRepository.findByBlockerAndBlockedUser(blocker, blockedUser) == null) {
            BlockedList blockedList = BlockedList.builder()
                    .blocker(blocker)
                    .blockedUser(blockedUser)
                    .build();
            blockedListRepository.save(blockedList);

            //차단한 순간, 차단된 사용자의 게시물에 누른 하트 제거
            List<BoardHeart> hearts = boardHeartRepository.findAllByUserAndBoard_User(blocker, blockedUser);
            for(BoardHeart heart : hearts) {
                boardHeartRepository.delete(heart);
            }

            ////차단한 순간, 차단된 사용자의 게시물에 누른 좋아요 제거
            List<BoardGood> goods = boardGoodRepository.findAllByUserAndBoard_User(blocker, blockedUser);
            for(BoardGood good : goods) {
                boardGoodRepository.delete(good);
            }
        }
    }

    public void unblockUser(String username, BoardDto.UnblockUserReqDto unblockReqDto) {
        User blocker = userRepository.findByUsername(username);
        User blockedUser = userRepository.findByUsername(unblockReqDto.getBlockedUserName());
        if(blocker == null || blockedUser == null) {
            throw new CustomException(CustomResponseStatus.USER_NOT_FOUND);
        }

        BlockedList blockedList = blockedListRepository.findByBlockerAndBlockedUser(blocker, blockedUser);
        blockedListRepository.delete(blockedList);

    }


}
