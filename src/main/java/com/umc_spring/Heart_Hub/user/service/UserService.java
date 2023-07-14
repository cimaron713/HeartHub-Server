package com.umc_spring.Heart_Hub.user.service;

import com.umc_spring.Heart_Hub.user.dto.UserDTO;

public interface UserService {
    Boolean register(UserDTO.SignUpRequest request);
    Boolean validateDuplicateEmail(String email);
    UserDTO.LoginResponse login(UserDTO.LoginRequest request);

    UserDTO.GetUserInfoResponse getUserInfo(UserDTO.GetUserInfoRequest request);
}
