package com.umc_spring.Heart_Hub.constant.exception;

import com.umc_spring.Heart_Hub.constant.enums.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exception")
@Slf4j
public class ExceptionController {

    @GetMapping("/entrypoint")
    public void entrypointException() {
        log.info("entryPoint Controller entered");
        throw new CustomException(ErrorCode.AUTHENTICATION_FAILED);
    }

    @GetMapping("/entrypoint/nullToken")
    public void nullTokenException() {
        log.info("nullTokenException Controller entered");
        throw new CustomException(ErrorCode.AUTHENTICATION_FAILED);
    }

    @GetMapping("/entrypoint/expiredToken")
    public void expiredTokenException() {
        log.info("expiredTokenException Controller entered");
        throw new CustomException(ErrorCode.EXPIRED_JWT);
    }

    @GetMapping("/entrypoint/badToken")
    public void badTokenException() {
        log.info("badTokenException Controller entered");
        throw new CustomException(ErrorCode.AUTHENTICATION_FAILED);
    }

    @GetMapping("/accessDenied")
    public void accessDeniedException() {
        log.info("accessDeniedException Controller entered");
        throw new CustomException(ErrorCode.AUTHORIZATION_FAILED);
    }
}
