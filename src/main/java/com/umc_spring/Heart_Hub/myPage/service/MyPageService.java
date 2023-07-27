package com.umc_spring.Heart_Hub.myPage.service;


import com.umc_spring.Heart_Hub.board.repository.community.BoardRepository;
import com.umc_spring.Heart_Hub.board.service.community.BoardService;
import com.umc_spring.Heart_Hub.myPage.dto.MyPageDto;
import com.umc_spring.Heart_Hub.user.model.User;
import com.umc_spring.Heart_Hub.user.repository.UserRepository;
import org.springframework.stereotype.Service;


@Service
public class MyPageService {
    private UserRepository userRepository;
    private BoardRepository boardRepository;
    private BoardService boardService;
    /**
     * 유저프로필 페이지 프로필 조회
     */
    public MyPageDto.Response myPageDetail(String userName){
        User user = userRepository.findByUsername(userName);
        MyPageDto.Response response = MyPageDto.Response
                .builder()
                .user(user)
                .build();
        return response;
    };

    /**
     * 유저프로필 페이지 수정
     */
    public void myPageProfileUpdate(String userName, MyPageDto.Request request){
        User user = userRepository.findByUsername(userName);
        if(!request.getMyImgUrl().equals(user.getUserImgUrl())){
            user.modifyUserImgUrl(request.getMyImgUrl());
        }
        if(!request.getUserMessage().equals(user.getUserMessage())){
            user.modifyUserMessage(request.getUserMessage());
        }
        if(!request.getUserName().equals(user.getNickname())){
            user.modifyUserImgUrl(request.getMyImgUrl());
        }
    };

    /**
     * 마이페이지 메뉴 상단 유저 프로필 조회
     */
    public MyPageDto.MyPage myProfileMenu(String userName){
        User user = userRepository.findByUsername(userName);
        MyPageDto.MyPage result = MyPageDto.MyPage.builder()
                .user(user)
                .build();
        return result;
    }


}

