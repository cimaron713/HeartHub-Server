package com.umc_spring.Heart_Hub.constant.exception;

import com.umc_spring.Heart_Hub.constant.enums.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CustomException extends RuntimeException{
    ErrorCode errorCode;
}
