package com.umc_spring.Heart_Hub.security.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;


@Service
public class CookieUtils {
    private CookieUtils(){}
    public static ResponseCookie createCookie(String cookieName, String value) {
        return ResponseCookie.from(cookieName, value)
                .path("/")
                .secure(true)
                .sameSite("None")
                .httpOnly(true)
                .maxAge(JwtUtils.REFRESH_TOKEN_VALID_TIME / 1000)
                .build();
    }

    public static Cookie getCookie(HttpServletRequest request, String cookieName){
        if(request.getCookies().length == 0){
            return null;
        } else {
            Cookie[] cookies = request.getCookies();
            for(Cookie cookie : cookies){
                if(cookie.getName().equals(cookieName)){
                    return cookie;
                }
            }
        }
        return null;
    }
}