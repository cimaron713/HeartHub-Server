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
        if(findUser != null) {
            return true; // 존재한다면 true 반환
        }
        else{
            return false; // 존재하지 않으면 false 반환
        }
    }


    @Override
    public Boolean validateDuplicateUsername(String username){
        User findUser = userRepository.findByUsername(username);
        if(findUser != null) {
            return true; // 존재한다면 true 반환
        }
        else {
            return false; // 존재하지 않으면 false 반환
        }
    }

    @Override
    public Boolean findUsername(UserDTO.FindUsernameRequest request) throws Exception{
        User user = userRepository.findByEmail(request.getEmail());
        emailService.sendUsername(request.getEmail(), user.getUsername());
        return true;
    }

    @Override
    public Boolean findPw(UserDTO.FindPwRequest request) throws Exception{
        User user = userRepository.findByEmail(request.getEmail());
        if(user.getUsername().equals(request.getUsername())){
            String code = emailService.sendTemporaryPasswd(request.getEmail());
            user.setPassword(passwordEncoder.encode(code));
            userRepository.save(user);
            return true;
        }
        else{
             return false;
        }
    }

    @Override
    public UserDTO.LoginResponse login(UserDTO.LoginRequest request){
        User user = userRepository.findByUsername(request.getUsername());
        UsernamePasswordAuthenticationToken authenticationToken = request.toAuthentication();
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        String accessToken = jwtUtils.createToken(user.getUsername(), JwtUtils.TOKEN_VALID_TIME);
        String refreshToken = redisUtils.getData("RT:"+user.getUsername());

        if(refreshToken == null) {
            refreshToken = jwtUtils.createToken(user.getUsername(), JwtUtils.REFRESH_TOKEN_VALID_TIME);
            redisUtils.setDataExpire("RT:" + user.getUsername(), refreshToken, JwtUtils.REFRESH_TOKEN_VALID_TIME);
        }
        UserDTO.LoginResponse response = UserDTO.LoginResponse.builder()
                .token(accessToken)
                .build();
        return response;
    }

    public UserDTO.GetUserInfoResponse getUserInfo(UserDTO.GetUserInfoRequest request) {
        User user = userRepository.findByUserId(request.getUserId());
        if(user == null) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        return UserDTO.GetUserInfoResponse.of(user);
    }

    @Override
    public Boolean mateMatching(UserDTO.MateMatchRequest request){
        User currentUser = userRepository.findByUsername(request.getCurrentUsername());
        User mateUser = userRepository.findByUsername(request.getMateName());
        if(currentUser.getUser() == null){
            currentUser.setUser(mateUser);
            mateUser.setUser(currentUser);
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public UserDTO.GetDday getDday(String username) {
        User findUser = userRepository.getDdayByUserName(username);

        UserDTO.GetDday dDay = UserDTO.GetDday.builder()
                .dDay(findUser.getDDay())
                .build();

        return dDay;
    }

    @Override
    public Boolean changePassword(UserDTO.ChangePasswordRequest request){
        String username = jwtUtils.getUsername(request.getToken());
        User user = userRepository.findByUsername(username);
        System.out.println(username);
        System.out.println(user);

        if(passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())){
            user.setPassword(passwordEncoder.encode(request.getChangePassword()));
            userRepository.save(user);
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public UserDTO.MateExistenceDto checkMateExist(String username) {
        User user = userRepository.findByUsername(username);
        Boolean hasMate = user.getUser() != null;
        return new UserDTO.MateExistenceDto(hasMate);
    }

}
