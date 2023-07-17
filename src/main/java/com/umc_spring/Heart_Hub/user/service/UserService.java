package com.umc_spring.Heart_Hub.user.service;

import com.umc_spring.Heart_Hub.user.dto.UserDTO;

public interface UserService {
    Boolean register(UserDTO.SignUpRequest request);
    Boolean validateDuplicateEmail(String email);
    Boolean validateDuplicateId(String id);
    UserDTO.LoginResponse login(UserDTO.LoginRequest request);
    Boolean findId(UserDTO.findIdRequest request) throws Exception;
    Boolean findPw(UserDTO.findPwRequest request) throws Exception;
    UserDTO.GetUserInfoResponse getUserInfo(UserDTO.GetUserInfoRequest request);
}
