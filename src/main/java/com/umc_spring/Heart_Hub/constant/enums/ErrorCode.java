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
    EXPIRED_JWT(HttpStatus.UNAUTHORIZED, "만료된 토큰입니다."),
    MALFORMED_JWT(HttpStatus.BAD_REQUEST, "잘못된 JWT 서명입니다."),
    UNSUPPORTED_JWT(HttpStatus.BAD_REQUEST, "지원되지 않는 JWT 토큰입니다."),
    BAD_JWT(HttpStatus.BAD_REQUEST, "JWT 토큰이 잘못되었습니다."),
    INVALID_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, "Refresh Token이 일치하지 않습니다."),
    LOGIN_FAILED(HttpStatus.UNAUTHORIZED, "로그인에 실패하였습니다."),
    AUTHENTICATION_FAILED(HttpStatus.BAD_REQUEST, "정상적인 JWT가 아닙니다."),
    REFRESHTOKEN_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 RefreshToken이 존재하지 않습니다."),
    AUTHORIZATION_FAILED(HttpStatus.UNAUTHORIZED, "권한이 없습니다."),
    NOT_PERMIT(HttpStatus.BAD_REQUEST, "누적 신고수 때문에 로그인 못합니다"),
    ALREADY_REPORTED(HttpStatus.BAD_REQUEST, "이미 신고한 사용자입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
