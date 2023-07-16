package com.umc_spring.Heart_Hub.user.service.impl;

import com.umc_spring.Heart_Hub.constant.enums.ErrorCode;
import com.umc_spring.Heart_Hub.constant.exception.CustomException;
import com.umc_spring.Heart_Hub.email.EmailService;
import com.umc_spring.Heart_Hub.user.dto.UserDTO;
import com.umc_spring.Heart_Hub.user.model.Role;
import com.umc_spring.Heart_Hub.user.model.User;
import com.umc_spring.Heart_Hub.user.repository.UserRepository;
import com.umc_spring.Heart_Hub.user.service.UserService;
import com.umc_spring.Heart_Hub.security.util.JwtUtils;
import com.umc_spring.Heart_Hub.security.util.RedisUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final RedisUtils redisUtils;
    private final EmailService emailService;

    @Override
    public Boolean register(UserDTO.SignUpRequest signUpRequest){
        User user = User.builder()
                .username(signUpRequest.getUsername())
                .id(signUpRequest.getId())
                .email(signUpRequest.getEmail())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .birth(signUpRequest.getBirth())
                .gender(signUpRequest.getGender())
                .nickname(signUpRequest.getNickname())
                .role(Role.ROLE_USER)
                .marketingStatus(signUpRequest.getMarketingStatus())
                .status("T")
                .build();
        userRepository.save(user);
        return true;
    }

    @Override
    public Boolean validateDuplicateEmail(String email) {
        User findUser = userRepository.findByEmail(email);
        if (findUser != null) {
            return true; // 존재한다면 true 반환
        }
        else {
            return false; // 존재하지 않으면 false 반환
        }
    }


    @Override
    public Boolean validateDuplicateId(String id){
        User findUser = userRepository.findById(id);
        if (findUser != null) {
            return true; // 존재한다면 true 반환
        }
        else {
            return false; // 존재하지 않으면 false 반환
        }
    }
    @Override
    public UserDTO.LoginResponse login(UserDTO.LoginRequest request){
        User user = userRepository.findByEmail(request.getEmail());
        UsernamePasswordAuthenticationToken authenticationToken = request.toAuthentication();
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        String accessToken = jwtUtils.createToken(user.getEmail(), JwtUtils.TOKEN_VALID_TIME);
        String refreshToken = redisUtils.getData("RT:"+user.getEmail());

        if(refreshToken == null) {
            refreshToken = jwtUtils.createToken(user.getEmail(), JwtUtils.REFRESH_TOKEN_VALID_TIME);
            redisUtils.setDataExpire("RT:" + user.getEmail(), refreshToken, JwtUtils.REFRESH_TOKEN_VALID_TIME);
        }
        UserDTO.LoginResponse response = UserDTO.LoginResponse.builder()
                .token(accessToken)
                .build();
        return response;
    }
    @Override
    public String emailSend(String email) throws Exception {
        String code = emailService.sendSimpleMessage(email);
        return code;
    }
    @Override
    public Boolean emailCodeCheck(String code, String userInput) {
        if (code.equals(userInput)) {
            return true;
        }
        else{
            return false;
        }
    }

    public UserDTO.GetUserInfoResponse getUserInfo(UserDTO.GetUserInfoRequest request) {
        User user = userRepository.findByUserId(request.getUserId());
        if(user == null) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        return UserDTO.GetUserInfoResponse.of(user);
    }
}
