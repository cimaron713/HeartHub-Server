package com.umc_spring.Heart_Hub.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {;

    private final HttpStatus httpStatus;
    private final String message;
}
