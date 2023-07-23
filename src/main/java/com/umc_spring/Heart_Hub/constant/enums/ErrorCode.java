package com.umc_spring.Heart_Hub.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    // 404 : NOT FOUND
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자 정보를 찾을 수 없습니다."),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "게시물 정보를 찾을 수 없습니다."),

    MISSION_NOT_FOUND(HttpStatus.NOT_FOUND, "미션 정보를 찾을 수 없습니다."),
    USER_MISSION_STATUS_NOT_FOUND(HttpStatus.NOT_FOUND, "UMS 정보를 찾을 수 없습니다."),
    JWT_EXPIRED(HttpStatus.UNAUTHORIZED, "만료된 토큰입니다."),
    LOGIN_FAILED(HttpStatus.UNAUTHORIZED, "로그인에 실패하였습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
