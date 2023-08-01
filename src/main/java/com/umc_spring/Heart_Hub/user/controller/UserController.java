package com.umc_spring.Heart_Hub.user.controller;


import com.umc_spring.Heart_Hub.constant.dto.ApiResponse;
import com.umc_spring.Heart_Hub.constant.enums.CustomResponseStatus;
import com.umc_spring.Heart_Hub.email.EmailService;
import com.umc_spring.Heart_Hub.user.dto.UserDTO;
import com.umc_spring.Heart_Hub.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;
    private final EmailService emailService;

    @PostMapping(value = "/join")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<UserDTO.SignUpRespDto>> signUp(@RequestBody UserDTO.SignUpRequestDto signUpRequestDto) {
        log.info(signUpRequestDto.toString());
        log.info(signUpRequestDto.getDatingDate().toString());
        UserDTO.SignUpRespDto registeredUser = userService.register(signUpRequestDto);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(registeredUser, CustomResponseStatus.SUCCESS));
    }

    @GetMapping(value = "/check/email")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<Boolean>> duplicateEmailCheck(@RequestBody UserDTO.DuplicateEmailCheckRequest email) {
        Boolean response = userService.validateDuplicateEmail(email.getEmail());
        return ResponseEntity.ok().body(ApiResponse.createSuccess(response, CustomResponseStatus.SUCCESS));
    }

    @GetMapping(value = "/check/username")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<Boolean>> duplicateUsernameCheck(@RequestBody UserDTO.DuplicateUsernameCheckRequest id) {
        Boolean response = userService.validateDuplicateUsername(id.getUsername());
        return ResponseEntity.ok().body(ApiResponse.createSuccess(response, CustomResponseStatus.SUCCESS));
    }

    @PostMapping(value = "/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<UserDTO.LoginResponse>> login(@RequestBody UserDTO.LoginRequest user) {
        UserDTO.LoginResponse response = userService.login(user);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(response, CustomResponseStatus.SUCCESS));
    }

    @GetMapping(value = "/email-verification")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<String>> sendVerificationCode(@RequestBody UserDTO.sendVerificationCode email) throws Exception {
        String response = emailService.sendVerificationCode(email.getEmail());
        return ResponseEntity.ok().body(ApiResponse.createSuccess(response, CustomResponseStatus.SUCCESS));
    }

    @PostMapping(value = "/find/username")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<Boolean>> findUsername(@RequestBody UserDTO.FindUsernameRequest request) throws Exception {
        Boolean response = userService.findUsername(request);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(response, CustomResponseStatus.SUCCESS));
    }

    @PostMapping(value = "/find/passwd")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<Boolean>> findPasswd(@RequestBody UserDTO.FindPwRequest request) throws Exception {
        Boolean response = userService.findPw(request);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(response, CustomResponseStatus.SUCCESS));
    }


    @GetMapping("/user/info")
    public ResponseEntity<ApiResponse<UserDTO.GetUserInfoResponse>> getUserInfo(@RequestBody UserDTO.GetUserInfoRequest user) {
        UserDTO.GetUserInfoResponse response = userService.getUserInfo(user);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(response, CustomResponseStatus.SUCCESS));
    }

    @PostMapping("/user/set/mate")
    public ResponseEntity<ApiResponse<Boolean>> mateMatchingUser(@RequestBody UserDTO.MateMatchRequest request) {
        Boolean response = userService.mateMatching(request);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(response, CustomResponseStatus.SUCCESS));
    }

    @PostMapping("/user/change/passwd")
    public ResponseEntity<ApiResponse<Boolean>> changePassword(@RequestBody UserDTO.ChangePasswordRequest request) {
        Boolean response = userService.changePassword(request);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(response, CustomResponseStatus.SUCCESS));
    }

    @GetMapping("/user/datingDate")
    public ResponseEntity<ApiResponse<UserDTO.GetRespDatingDateDto>> getDatingDate(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        UserDTO.GetRespDatingDateDto dDay = userService.getDatingDate(userDetails.getUsername());
        return ResponseEntity.ok().body(ApiResponse.createSuccess(dDay, CustomResponseStatus.SUCCESS));
    }

    @GetMapping("/user/exist-mate")
    public ResponseEntity<ApiResponse<UserDTO.MateExistenceDto>> checkMateExist(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        UserDTO.MateExistenceDto mateExist = userService.checkMateExist(userDetails.getUsername());
        return ResponseEntity.ok().body(ApiResponse.createSuccess(mateExist, CustomResponseStatus.SUCCESS));
    }

    @PostMapping("/user/member/reissue")
    public ResponseEntity<ApiResponse<UserDTO.ReissueRespDto>> reissue(@RequestHeader("Authorization") String refreshToken) {
        UserDTO.ReissueRespDto reissueRespDto = userService.reissue(refreshToken);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(reissueRespDto, CustomResponseStatus.SUCCESS));
    }

    @PostMapping("/user/member/logout")
    public ResponseEntity<ApiResponse<String>> logout(@RequestHeader("Authorization") String accessToken) {
        log.info("enter logout controller");
        userService.logout(accessToken);
        return ResponseEntity.ok().body(ApiResponse.createSuccessWithNoContent(CustomResponseStatus.SUCCESS));
    }

    @PostMapping("/delete/user")
    public ResponseEntity<ApiResponse<Boolean>> withdrawUser(UserDTO.WithdrawReqDto withdrawReqDto){
        Boolean response = userService.withdrawUser(withdrawReqDto);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(response, CustomResponseStatus.SUCCESS));
    }

    @PostMapping("/modi/user/{userId}")
    @Transactional
    public ResponseEntity<ApiResponse<String>> modifyUserReportStatus(@PathVariable Long userId) {
        userService.modifyUserReportStatus(userId);
        return ResponseEntity.ok().body(ApiResponse.createSuccessWithNoContent(CustomResponseStatus.SUCCESS));
    }
}