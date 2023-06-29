package com.umc_spring.Heart_Hub.constant.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@Getter
public class ApiResponse <T>{
    private HttpStatus httpStatus;
    private T data;
    private String message;

    public static <T> ApiResponse<T> createSuccess(T data, String message) {
        return new ApiResponse<>(HttpStatus.OK, data, message);
    }

    public static ApiResponse<String> createSuccessWithNoContent(String message) {
        return new ApiResponse<>(HttpStatus.OK, null, message);
    }

    //Hibernate Validator에 의해 유효하지 않은 데이터로 인해 API 호출이 거불될때 반환
    public static ApiResponse<Map<String, String>> createFail(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();

        List<ObjectError> allErrors = bindingResult.getAllErrors();
        for (ObjectError error : allErrors) {
            if (error instanceof FieldError) {
                errors.put(((FieldError) error).getField(), error.getDefaultMessage());
            } else {
                errors.put(error.getObjectName(), error.getDefaultMessage());
            }
        }
        return new ApiResponse<>(HttpStatus.BAD_REQUEST, errors, "FAIL");
    }

    // 예외 발생으로 API 호출 실패시 반환
    public static ApiResponse<String> createError(HttpStatus status, String errorMsg) {
        return new ApiResponse<>(status, errorMsg, "FAIL");
    }

    private ApiResponse(HttpStatus status, T data, String message) {
        this.httpStatus = status;
        this.data = data;
        this.message = message;
    }
}
