package com.example.alchohol.user.utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private static Key getKey(String key) {
        byte[] keyByte = key.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyByte);
    }

    public String generateToken(String userEmail, String key, Long expiredTime) {
        Claims claims = Jwts.claims();
        claims.put("userEmail", userEmail);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+expiredTime))
                .signWith(getKey(key), SignatureAlgorithm.HS256)
                .compact();
    }

}
