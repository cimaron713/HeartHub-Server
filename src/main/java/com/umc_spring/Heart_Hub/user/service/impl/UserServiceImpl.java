package com.umc_spring.Heart_Hub.user.service.impl;

import com.umc_spring.Heart_Hub.Report.model.enums.ReportStatus;
import com.umc_spring.Heart_Hub.constant.enums.CustomResponseStatus;
import com.umc_spring.Heart_Hub.constant.exception.CustomException;
import com.umc_spring.Heart_Hub.email.EmailService;
import com.umc_spring.Heart_Hub.user.dto.UserDTO;
import com.umc_spring.Heart_Hub.user.model.enums.Role;
import com.umc_spring.Heart_Hub.user.model.User;
import com.umc_spring.Heart_Hub.user.repository.UserRepository;
import com.umc_spring.Heart_Hub.user.service.UserService;
import com.umc_spring.Heart_Hub.security.util.JwtUtils;
import com.umc_spring.Heart_Hub.security.util.RedisUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final RedisUtils redisUtils;
    private final EmailService emailService;

    private final RedisTemplate redisTemplate;

    @Override
    public  UserDTO.SignUpRespDto register(UserDTO.SignUpRequestDto signUpRequestDto) {
        User user = User.builder()
                .username(signUpRequestDto.getUsername())
                .email(signUpRequestDto.getEmail())
                .password(passwordEncoder.encode(signUpRequestDto.getPassword()))
                .birth(signUpRequestDto.getBirth())
                .gender(signUpRequestDto.getGender())
                .nickname(signUpRequestDto.getNickname())
                .role(Role.ROLE_USER)
                .marketingStatus(signUpRequestDto.getMarketingStatus())
                .status("T")
                .datingDate(signUpRequestDto.getDatingDate())
                .reportedStatus(ReportStatus.NORMAL)
                .build();
        userRepository.save(user);

        UserDTO.MateMatchRequest request = UserDTO.MateMatchRequest.builder()
                .mateName(signUpRequestDto.getMate())
                .currentUsername(signUpRequestDto.getUsername())
                .build();
        mateMatching(request);

        return UserDTO.SignUpRespDto.builder()
                .nickname(user.getNickname())
                .build();
    }

    @Override
    public Boolean validateDuplicateEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (!user.isEmpty()) {
            return true; // 존재한다면 true 반환
        } else {
            return false; // 존재하지 않으면 false 반환
        }
    }


    @Override
    public Boolean validateDuplicateUsername(String username) {
        User findUser = userRepository.findByUsername(username);
        if (findUser != null) {
            return true; // 존재한다면 true 반환
        } else {
            return false; // 존재하지 않으면 false 반환
        }
    }

    @Override
    public Boolean findUsername(UserDTO.FindUsernameRequest request) throws Exception {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> {
            throw new CustomException(CustomResponseStatus.USER_NOT_FOUND);
        });
        emailService.sendUsername(request.getEmail(), user.getUsername());
        return true;
    }

    @Override
    public Boolean findPw(UserDTO.FindPwRequest request) throws Exception {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> {
            throw new CustomException(CustomResponseStatus.USER_NOT_FOUND);
        });
        if (user.getUsername().equals(request.getUsername())) {
            String code = emailService.sendTemporaryPasswd(request.getEmail());
            user.changePassword(passwordEncoder.encode(code));
            userRepository.save(user);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public UserDTO.LoginResponse login(UserDTO.LoginRequest loginRequest) {
        log.info(loginRequest.toString());
        User user = userRepository.findByUsername(loginRequest.getUsername());

        if (user == null) {
            throw new CustomException(CustomResponseStatus.USER_NOT_FOUND);
        }

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new CustomException(CustomResponseStatus.LOGIN_FAILED);
        }

        if(user.getReportedStatus().equals(ReportStatus.ACCOUNT_SUSPENDED)) {
            throw new CustomException(CustomResponseStatus.NOT_PERMIT);
        }
        String accessToken = jwtUtils.createToken(user.getEmail(), JwtUtils.TOKEN_VALID_TIME);
        String refreshToken = redisUtils.getData("RT:" + user.getEmail());

        if (refreshToken == null) {
            // refreshToken이 존재하지 않는다면 설정해줘야함
            String newRefreshToken = jwtUtils.createToken(user.getEmail(), JwtUtils.REFRESH_TOKEN_VALID_TIME);
            log.info("newRefreshToken : "+newRefreshToken);
            redisUtils.setDataExpire("RT:" + user.getEmail(), newRefreshToken, JwtUtils.REFRESH_TOKEN_VALID_TIME_IN_REDIS);
            refreshToken = newRefreshToken;
        }

        return UserDTO.LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .accessTokenExpirationTime(JwtUtils.TOKEN_VALID_TIME)
                .build();

    }

    public UserDTO.GetUserInfoResponse getUserInfo(UserDTO.GetUserInfoRequest request) {
        User user = userRepository.findByUserId(request.getUserId());
        if (user == null) {
            throw new CustomException(CustomResponseStatus.USER_NOT_FOUND);
        }

        return UserDTO.GetUserInfoResponse.of(user);
    }

    @Override
    public Boolean mateMatching(UserDTO.MateMatchRequest request) {
        User currentUser = userRepository.findByUsername(request.getCurrentUsername());
        User mateUser = userRepository.findByUsername(request.getMateName());
        if (mateUser == null) {
            return false;
        } else {
            if (currentUser.getUser() == null && mateUser.getUser() == null) {
                currentUser.mateMatching(mateUser);
                mateUser.mateMatching(currentUser);
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public UserDTO.GetRespDatingDateDto getDatingDate(String username) {
        User findUser = userRepository.getDatingDateByUserName(username);

        return UserDTO.GetRespDatingDateDto.builder()
                .datingDate(findUser.getDatingDate())
                .build();
    }

    @Override
    public Boolean changePassword(UserDTO.ChangePasswordRequest request) {
        String email = jwtUtils.getEmailInToken(request.getToken());
        User user = userRepository.findByEmail(email).orElseThrow(() -> {
            throw new CustomException(CustomResponseStatus.USER_NOT_FOUND);
        });
        log.info("email : " + email);
        log.info("user : " + user);

        if (passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            user.changePassword(passwordEncoder.encode(request.getChangePassword()));
            userRepository.save(user);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public UserDTO.MateExistenceDto checkMateExist(String username) {
        User user = userRepository.findByUsername(username);
        Boolean hasMate = user.getUser() != null;
        return new UserDTO.MateExistenceDto(hasMate);
    }

    @Override
    public UserDTO.ReissueRespDto reissue(String refreshToken) {
        String resolvedToken = jwtUtils.resolveToken(refreshToken);
        String email = jwtUtils.getEmailInToken(resolvedToken);
        String savedRefreshToken = redisUtils.getData("RT:" + email);
        log.info("savedRefreshToken : "+savedRefreshToken);
        log.info("RefreshToken : "+resolvedToken);
        if (refreshToken.isEmpty() || !resolvedToken.equals(savedRefreshToken)) {
            throw new CustomException(CustomResponseStatus.INVALID_REFRESH_TOKEN);
        } else {
            String newAccessToken = jwtUtils.createToken(email, JwtUtils.TOKEN_VALID_TIME);
            String newRefreshToken = jwtUtils.createToken(email, JwtUtils.REFRESH_TOKEN_VALID_TIME);
            redisUtils.setDataExpire("RT:" + email, newRefreshToken, JwtUtils.REFRESH_TOKEN_VALID_TIME_IN_REDIS);
            String getToken = redisUtils.getData("RT:"+email);

            return UserDTO.ReissueRespDto.builder()
                    .newAccessToken(newAccessToken)
                    .newRefreshToken(newRefreshToken)
                    .accessTokenExpirationTime(JwtUtils.TOKEN_VALID_TIME)
                    .build();
        }

    }

    @Override
    public void logout(String accessToken) {
        String resolvedToken = jwtUtils.resolveToken(accessToken);

        String email = jwtUtils.getEmailInToken(resolvedToken);
        String data = redisUtils.getData("RT:" + email);
        if(data != null) {
            redisUtils.deleteData("RT:" + email);
        } else {
            throw new CustomException(CustomResponseStatus.REFRESHTOKEN_NOT_FOUND);
        }

        redisUtils.setDataExpire(resolvedToken, "logout", jwtUtils.getExpiration(resolvedToken));
    }

    @Override
    public Boolean withdrawUser(String accessToken){
        String resolveToken = jwtUtils.resolveToken(accessToken);
        String email = jwtUtils.getEmailInToken(resolveToken);
        User user = userRepository.findByEmail(email).orElseThrow(() -> {
            throw new CustomException(CustomResponseStatus.USER_NOT_FOUND);
        });
        user.modifyUserDeleteStatus();
        logout(accessToken);
        return true;
    }

    @Override
    public void modifyUserReportStatus(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> {
            throw new CustomException(CustomResponseStatus.USER_NOT_FOUND);
        });
        user.modifyUserReportStatus();
    }

    @Override
    public void modifyUserAuthority(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> {
            throw new CustomException(CustomResponseStatus.USER_NOT_FOUND);
        });
        user.modifyUserAuthority();
    }


}
