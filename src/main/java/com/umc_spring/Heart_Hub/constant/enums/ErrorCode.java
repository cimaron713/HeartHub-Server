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

    MISSION_NOT_FOUNT(HttpStatus.NOT_FOUND, "미션 정보를 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
