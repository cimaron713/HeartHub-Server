package com.umc_spring.Heart_Hub.user.service;

import com.umc_spring.Heart_Hub.user.dto.UserDTO;

public interface UserService {
    UserDTO.SignUpRespDto register(UserDTO.SignUpRequestDto request);
    Boolean validateDuplicateEmail(String email);
    Boolean validateDuplicateUsername(String id);
    UserDTO.LoginResponse login(UserDTO.LoginRequest request);
    Boolean findUsername(UserDTO.FindUsernameRequest request) throws Exception;
    Boolean findPw(UserDTO.FindPwRequest request) throws Exception;
    UserDTO.GetUserInfoResponse getUserInfo(UserDTO.GetUserInfoRequest request);
    Boolean mateMatching(UserDTO.MateMatchRequest request);
    Boolean changePassword(UserDTO.ChangePasswordRequest request);
    UserDTO.GetRespDatingDateDto getDatingDate(String username);
    UserDTO.MateExistenceDto checkMateExist(String username);

    UserDTO.ReissueRespDto reissue(String refreshToken);
    void logout(String accessToken);
    Boolean withdrawUser(UserDTO.WithdrawReqDto withdrawReqDto);

    void modifyUserReportStatus(Long userId);
}
