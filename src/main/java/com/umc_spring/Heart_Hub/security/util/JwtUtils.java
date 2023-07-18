package com.umc_spring.Heart_Hub.security.util;

import com.umc_spring.Heart_Hub.user.service.impl.UserDetailsServiceImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;


import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Service
@RequiredArgsConstructor
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

    public String getUsername(String token){
        return extractAllClaims(token).get("username", String.class);
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }


    public String createToken(String username, long expireTime) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("username", username);
        String jwt = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(getSigningKey(SECRET_KEY), SignatureAlgorithm.HS256)
                .compact();

        return jwt;
    }



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
            return false;
        }
    }

    public Long getExpiration(String jwtToken) {
        Date expiration = Jwts.parserBuilder().setSigningKey(getSigningKey(SECRET_KEY)).build()
                .parseClaimsJws(jwtToken).getBody().getExpiration();
        long now = System.currentTimeMillis();
        return expiration.getTime() - now;
    }


}