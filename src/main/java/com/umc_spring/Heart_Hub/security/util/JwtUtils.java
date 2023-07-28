package com.umc_spring.Heart_Hub.security.util;

import com.umc_spring.Heart_Hub.Report.model.enums.ReportStatus;
import com.umc_spring.Heart_Hub.constant.enums.ErrorCode;
import com.umc_spring.Heart_Hub.constant.exception.CustomException;
import com.umc_spring.Heart_Hub.user.model.User;
import com.umc_spring.Heart_Hub.user.repository.UserRepository;
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
import java.util.Base64;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public final class JwtUtils {
    private final UserRepository userRepository;
    private final UserDetailsServiceImpl userDetailsService;

    private final String SECRET_KEY = "saldkfjlaksfjlitulkasjgklasghisaouytlasjktkalthlkjas";

    public static final String REFRESH_TOKEN_NAME = "refresh_token";
    public static final long TOKEN_VALID_TIME = 1000L * 60 * 5; // 5분
    public static final long REFRESH_TOKEN_VALID_TIME = 1000L * 60 * 60 * 144; // 1주일
    public static final long REFRESH_TOKEN_VALID_TIME_IN_REDIS = 60 * 60 * 24 * 7; // 1주일


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

    public String getEmailInToken(String token) {
        return extractAllClaims(token).get("email", String.class);
    }

    // 권한정보 획득
    // Spring Security 인증과정에서 권한 확인을 위해 사용
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getEmailInToken(token));
        log.info("userDetails.getUsername : "+userDetails.getUsername());
        log.info("userDetails.getPassword : "+ userDetails.getPassword());
        log.info("getAuthorities() : "+userDetails.getAuthorities().toString());
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // username(id)를 claim에 넣어서 사용할경우 보안상 좋지 않음 대체할것이 필요
    // username -> email 으로 변경
    public String createToken(String email, long expireTime) {
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("email", email);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(getSigningKey(SECRET_KEY), SignatureAlgorithm.HS256)
                .compact();
    }

    //
    public String resolveToken(String token){
        if(token != null){
            return token.substring("Bearer ".length());
        } else {
            return "";
        }
    }

    public boolean validateToken(String jwtToken) {
//        try {
//            log.info("come in validateToken method");
//            Claims body = Jwts.parserBuilder().setSigningKey(getSigningKey(SECRET_KEY)).build().parseClaimsJws(jwtToken).getBody();
//            log.info("email : "+body.get("email"));
//            return true;
//        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
//            log.info("잘못된 JWT 서명입니다.");
//            return false;
//        } catch (ExpiredJwtException e) {
//            log.info("만료된 JWT 토큰입니다.");
//            return false;
//        } catch (UnsupportedJwtException e) {
//            log.info("지원되지 않는 JWT 토큰입니다.");
//            return false;
//        } catch (IllegalArgumentException e) {
//            log.info("JWT 토큰이 잘못되었습니다.");
//        }
//        return false;
        try {
//            String encodedJwtToken = Base64.getEncoder().encodeToString(jwtToken.getBytes());
            log.info("Enter the validateToken Method");
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(getSigningKey(SECRET_KEY)).build().parseClaimsJws(jwtToken);
            log.info("email : "+claims.getBody().get("email"));

            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public Long getExpiration(String jwtToken) {
        Date expiration = Jwts.parserBuilder().setSigningKey(getSigningKey(SECRET_KEY)).build()
                .parseClaimsJws(jwtToken).getBody().getExpiration();
        long now = System.currentTimeMillis();
        return expiration.getTime() - now;
    }

    public boolean getReportedStatusByToken(String token) {
        User user = userRepository.findByEmail(getEmailInToken(token)).orElseThrow(() -> {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        });

        log.info("user Reported Status : "+user.getReportedStatus().toString());
        if(user.getReportedStatus().equals(ReportStatus.ACCOUNT_SUSPENDED)) {
            log.info("Reported Status bad");
            return false;
        } else {
            log.info("Reported Status good");
            return true;
        }
    }


}