package com.umc_spring.Heart_Hub.constant.enums;

import lombok.Getter;

@Getter
public enum CustomResponseStatus {

    // 1000 : 요청 성공
    SUCCESS(true, 1000, "요청에 성공하였습니다."),

    // 2000 : BAD REQUEST
    MALFORMED_JWT(false, 2000, "잘못된 JWT 서명입니다."),
    UNSUPPORTED_JWT(false, 2001, "지원되지 않는 JWT 토큰입니다."),
    BAD_JWT(false, 2000, "JWT 토큰이 잘못되었습니다."),
    INVALID_REFRESH_TOKEN(false, 2002, "Refresh Token이 일치하지 않습니다."),
    AUTHENTICATION_FAILED(false, 2003, "정상적인 JWT가 아닙니다."),
    REFRESHTOKEN_NOT_FOUND(false, 2004, "해당 RefreshToken이 존재하지 않습니다."),
    NOT_PERMIT(false, 2005, "누적 신고수 때문에 로그인 못합니다"),
    ALREADY_REPORTED(false, 2006, "이미 신고한 사용자입니다."),
    DUPLICATION_EMAIL(false, 2007, "중복된 Email입니다"),
    DUPLICATION_USERNAME(false, 2008, "중복된 Username입니다."),
    DUPLICATION_NICKNAME(false, 2009, "중복된 Nickname입니다."),
    USER_NOT_MATCH(false, 2010, "유저가 일치하지 않습니다."),
    ALREADY_GOOD(false,2011,"이미 좋아요를 누른 게시물입니다."),
    COMMENT_REPLY_NOT_GOOD(false,2012,"대댓글은 좋아요를 누를 수 없습니다."),

    // 3000 : UNAUTHORIZED
    EXPIRED_JWT(false, 3000, "만료된 토큰입니다."),
    LOGIN_FAILED_USERNAME(false, 3001, "아이디가 틀렸습니다."),
    LOGIN_FAILED_PWD(false, 3002, "비밀번호가 틀렸습니다."),
    AUTHORIZATION_FAILED(false, 3003, "권한이 없습니다."),

    // 4000 : NOT FOUND
    USER_NOT_FOUND(false, 4000, "사용자 정보를 찾을 수 없습니다."),
    POST_NOT_FOUND(false, 4001, "게시물 정보를 찾을 수 없습니다."),
    COMMENT_NOT_FOUND(false, 4002, "댓글 정보를 찾을 수 없습니다."),
    COMMENT_PARENT_NOT_FOUND(false, 4002, "부모 댓글 정보를 찾을 수 없습니다."),

    MISSION_NOT_FOUND(false, 4003, "미션 정보를 찾을 수 없습니다."),
    USER_MISSION_STATUS_NOT_FOUND(false, 4004, "UMS 정보를 찾을 수 없습니다."),
    IMAGE_NOT_UPLOAD(false, 4005, "이미지가 업로드되지 않았습니다."),

    // 5000 : SERVER ERROR
    INTERNAL_SERVER_ERROR(false, 5000, "내부 서버 오류입니다.");


    private final boolean isSuccess;
    private final int code;
    private final String message;

    private CustomResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
