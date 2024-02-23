package com.example.springsecurityexample.adapter;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JwtAdapter {


    private final SecretKey key = Jwts.SIG.HS256.key().build();
    @Value("${jwt.expiration-time}")
    private long expirationTime;

    public String createToken(String email) {

        Instant instant = LocalDateTime.now().plusMinutes(expirationTime).atZone(ZoneId.systemDefault()).toInstant();

        Date expiration = Date.from(instant);

        return Jwts.builder()
                .subject(email)
                .expiration(expiration)
                .signWith(key)
                .compact();
    }

    public String getEmail(String token) {
        try {

        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token).getPayload().getSubject();


        } catch (Exception e) {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }
    }
}
