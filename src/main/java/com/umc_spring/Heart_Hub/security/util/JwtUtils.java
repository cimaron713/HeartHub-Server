package com.umc_spring.Heart_Hub.security.util;

import com.umc_spring.Heart_Hub.constant.enums.ErrorCode;
import com.umc_spring.Heart_Hub.constant.exception.CustomException;
import com.umc_spring.Heart_Hub.user.service.impl.UserDetailsServiceImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;


import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public final class JwtUtils {
    private final UserDetailsServiceImpl userDetailsService;

    private final String SECRET_KEY = "saldkfjlaksfjlitulkasjgklasghisaouytlasjktkalthlkjas";

    public static final String REFRESH_TOKEN_NAME = "refresh_token";
    public static final long TOKEN_VALID_TIME = 1000L * 60 * 60; // 1시간만 토큰 유효
    public static final long REFRESH_TOKEN_VALID_TIME = 1000L * 60 * 60 * 144; // 1주일

    public Key getSigningKey(String secretKey){
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public Claims extractAllClaims(String jwtToken){
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey(SECRET_KEY))
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
    }

//    public String getUsername(String token){
//        return extractAllClaims(token).get("username", String.class);
//    }

    public String getEmailInToken(String token) {
        return extractAllClaims(token).get("email", String.class);
    }

    // 권한정보 획득
    // Spring Security 인증과정에서 권한 확인을 위해 사용
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getEmailInToken(token));
        log.info("new UsernamePasswordAuthenticationToken(userDetails, \"\", userDetails.getAuthorities() : " + new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities()));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // username(id)를 claim에 넣어서 사용할경우 보안상 좋지 않음 대체할것이 필요
    // username -> email 으로 변경
    public String createToken(String email, long expireTime) {
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("email", email);
        String jwt = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(getSigningKey(SECRET_KEY), SignatureAlgorithm.HS256)
                .compact();

        return jwt;
    }

    //
    public String resolveToken(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        if(token != null){
            return token.substring("Bearer ".length());
        } else {
            return "";
        }
    }

    public boolean validateToken(String jwtToken){
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(getSigningKey(SECRET_KEY)).build().parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());

        } catch (Exception e){
            throw new CustomException(ErrorCode.JWT_EXPIRED);
        }
    }

    public Long getExpiration(String jwtToken) {
        Date expiration = Jwts.parserBuilder().setSigningKey(getSigningKey(SECRET_KEY)).build()
                .parseClaimsJws(jwtToken).getBody().getExpiration();
        long now = System.currentTimeMillis();
        return expiration.getTime() - now;
    }


}