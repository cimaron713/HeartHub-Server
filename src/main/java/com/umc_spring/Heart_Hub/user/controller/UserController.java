package com.umc_spring.Heart_Hub.user.controller;


import com.umc_spring.Heart_Hub.constant.dto.ApiResponse;
import com.umc_spring.Heart_Hub.email.EmailService;
import com.umc_spring.Heart_Hub.user.dto.UserDTO;
import com.umc_spring.Heart_Hub.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    private final EmailService emailService;

    @Autowired
    public UserController(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    @PostMapping(value = "/join")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<Boolean>> signUp(@RequestBody UserDTO.SignUpRequest user) {
        Boolean response = userService.register(user);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(response, "Success!"));
    }
    @PostMapping(value = "/check/email")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<Boolean>> duplicateEmailCheck(@RequestBody UserDTO.DuplicateEmailCheckRequest email){
        Boolean response = userService.validateDuplicateEmail(email.getEmail());
        return ResponseEntity.ok().body(ApiResponse.createSuccess(response, "Success!"));
    }

    @PostMapping(value = "/check/id")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<Boolean>> duplicateIdCheck(@RequestBody UserDTO.DuplicateIdCheckRequest id){
        Boolean response = userService.validateDuplicateId(id.getId());
        return ResponseEntity.ok().body(ApiResponse.createSuccess(response, "Success!"));
    }

    @PostMapping(value = "/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<UserDTO.LoginResponse>> login(@RequestBody UserDTO.LoginRequest user){
        UserDTO.LoginResponse response = userService.login(user);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(response, "Success!"));
    }

    @PostMapping(value = "/email-verification")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<String>> sendVerificationCode(@RequestBody UserDTO.sendVerificationCode email) throws Exception{
        String response = emailService.sendVerificationCode(email.getEmail());
        return ResponseEntity.ok().body(ApiResponse.createSuccess(response, "Success!"));
    }

    @PostMapping(value = "/find/id")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<Boolean>> findId(@RequestBody UserDTO.findIdRequest request) throws Exception{
        Boolean response = userService.findId(request);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(response, "Success!"));
    }

    @PostMapping(value = "/find/passwd")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<Boolean>> findPasswd(@RequestBody UserDTO.findPwRequest request) throws Exception{
        Boolean response = userService.findPw(request);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(response, "Success!"));
    }


    @PostMapping("/user/info")
    public ResponseEntity<ApiResponse<UserDTO.GetUserInfoResponse>> getUserInfo(@RequestBody UserDTO.GetUserInfoRequest user) {
        UserDTO.GetUserInfoResponse response = userService.getUserInfo(user);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(response, "Success!"));
    }
}
