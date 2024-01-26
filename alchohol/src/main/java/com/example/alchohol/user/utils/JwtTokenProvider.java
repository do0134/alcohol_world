package com.example.alchohol.user.utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

public class JwtTokenProvider {

    public Boolean validate(String token, String userEmail, String key) {
        String userEmailByToken = getUserEmail(token,key);
        return userEmailByToken.equals(userEmail)&&!isTokenExpired(token,key);
    }

    public String generateToken(String userEmail, String key, Long expiredTime) {
        return doGenerateToken(userEmail, key, expiredTime);
    }

    public String getUserEmail(String token, String key) {
        return extractClaims(token, key).get("userEmail",String.class);
    }

    public String doGenerateToken(String userEmail, String key, Long expiredTime) {
        Claims claims = Jwts.claims();
        claims.put("userEmail", userEmail);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+expiredTime))
                .signWith(getKey(key), SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims extractClaims(String token, String key) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey(key))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token, String key) {
        Date expiration = extractClaims(token, key).getExpiration();
        return expiration.before(new Date());
    }

    private static Key getKey(String key) {
        byte[] keyByte = key.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyByte);
    }
}
