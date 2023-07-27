package com.umc_spring.Heart_Hub.constant.exception;

import com.umc_spring.Heart_Hub.constant.dto.ApiResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationException(BindingResult bindingResult) {
        log.info("Enter handleValidationException()");
        log.info("handlevalidateException실행");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.createFail(bindingResult));
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<String>> handleCustomException(CustomException e) {
//        log.error(e.getErrorCode().getMessage());
        log.info("handleCustomException실행");
        log.info("e.getHpptStatus : "+e.getErrorCode().getHttpStatus().toString());
        log.info("e.getMessage : "+e.getErrorCode().getMessage());
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(ApiResponse.createError(e.getErrorCode().getHttpStatus(), e.getErrorCode().getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleException(Exception e) {
        log.error(e.getMessage());
        log.info("handleException실행");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.createError(HttpStatus.INTERNAL_SERVER_ERROR, "내부 서버 오류입니다."));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<String>> handleAccessDeniedException(Exception e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.createError(HttpStatus.INTERNAL_SERVER_ERROR, "accessDeniedException"));
    }

//    @ExceptionHandler(ExpiredJwtException.class)
//    public ResponseEntity<ApiResponse<String>> handleExpiredJwtException(Exception e) {
//        log.error(e.getMessage());
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                .body(ApiResponse.createError(HttpStatus.UNAUTHORIZED, "Token Expired"));
//    }
//
//    @ExceptionHandler(UnsupportedJwtException.class)
//    public ResponseEntity<ApiResponse<String>> handleUnsupportedJwtException(Exception e) {
//        log.error(e.getMessage());
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                .body(ApiResponse.createError(HttpStatus.UNAUTHORIZED, "Token Unsupported"));
//    }
//
//    @ExceptionHandler(value = { SecurityException.class, MalformedJwtException.class })
//    public ResponseEntity<ApiResponse<String>> handleMalformedJwtException(Exception e) {
//        log.error(e.getMessage());
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                .body(ApiResponse.createError(HttpStatus.UNAUTHORIZED, "Token Malformed"));
//    }
}
