package com.umc_spring.Heart_Hub.security;

import com.umc_spring.Heart_Hub.constant.enums.ErrorCode;
import com.umc_spring.Heart_Hub.constant.exception.CustomException;
import com.umc_spring.Heart_Hub.security.util.JwtUtils;
import com.umc_spring.Heart_Hub.security.util.RedisUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;
    private final RedisUtils redisUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getServletPath();
        String token = jwtUtils.resolveToken(request);

        // 여기 path 없애도 될거같음
        if(!path.startsWith("/api/member/reissue") && !path.startsWith("/api/login")
                && !path.startsWith("/api/join")
                && token != null && jwtUtils.validateToken(token)){
            if(jwtUtils.getReportedStatusByToken(token)) {
                String isLogout = redisUtils.getData(token);
                if(isLogout == null){
                    Authentication authentication = jwtUtils.getAuthentication(token);
                    log.info("filter get Authorities() : "+authentication.getAuthorities().toString());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } else {
                throw new CustomException(ErrorCode.NOT_PERMIT);
            }
        }
        filterChain.doFilter(request, response);
    }
}