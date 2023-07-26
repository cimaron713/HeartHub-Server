package com.umc_spring.Heart_Hub.constant.exception;

import com.umc_spring.Heart_Hub.constant.enums.ErrorCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exception")
public class ExceptionController {

    @GetMapping("/entrypoint")
    public void entrypointException() {
        throw new CustomException(ErrorCode.AUTHENTICATION_FAILED);
    }

    @GetMapping("/accessDenied")
    public void accessDeniedException() {
        throw new CustomException(ErrorCode.AUTHORIZATION_FAILED);
    }
}
