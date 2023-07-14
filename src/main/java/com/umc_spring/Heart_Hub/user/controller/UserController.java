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

    @PostMapping(value = "/register")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Boolean> signUp(@RequestBody UserDTO.SignUpRequest user) {
        userService.register(user);
        return ResponseEntity.ok(true);
    }
    @PostMapping(value = "/check/email")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Boolean> duplicateEmailCheck(@RequestBody UserDTO.DuplicateEmailCheckRequest email){
        return ResponseEntity.ok(userService.validateDuplicateEmail(email.getEmail()));
    }

    @PostMapping(value = "/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserDTO.LoginResponse> login(@RequestBody UserDTO.LoginRequest user){
        return ResponseEntity.ok(userService.login(user));
    }

    @PostMapping(value = "/email-verification")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> sendVerificationCode(@RequestBody UserDTO.sendVerificationCode email) throws Exception{
        return ResponseEntity.ok(emailService.sendSimpleMessage(email.getEmail()));
    }

    @PostMapping("/users")
    public ResponseEntity<ApiResponse<UserDTO.GetUserInfoResponse>> getUserInfo(@RequestBody UserDTO.GetUserInfoRequest user) {
        UserDTO.GetUserInfoResponse response = userService.getUserInfo(user);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(response, "Success!"));
    }
}
