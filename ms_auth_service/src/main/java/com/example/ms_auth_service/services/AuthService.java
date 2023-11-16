package com.example.ms_auth_service.services;


import com.example.ms_auth_service.exceptions.UnauthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Slf4j
@Service
public class AuthService {


    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpirationInMs;

    @Value("${jwt.prefix}")
    private String prefix;

    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String validateToken(String authHeaderValue) {
        if (authHeaderValue == null || !authHeaderValue.startsWith(prefix)) {
            throw new UnauthorizedException("JWT token is missing or invalid");
        }
        String token = authHeaderValue.substring(7);

        return extractEmail(token);

    }


    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        try {
            final Claims claims = Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
            return claimsResolver.apply(claims);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new UnauthorizedException("JWT token is invalid");
        }

    }

    public String generateToken(String gmail) {
        return Jwts.builder()
                .setSubject(gmail)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMs))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }


}

