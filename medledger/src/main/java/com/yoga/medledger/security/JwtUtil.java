package com.yoga.medledger.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET_KEY = "medledger-secret-key-medledger-secret-key";

    public String generateToken(String email, String role) {

        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .compact();
    }

    public Claims extractAllClaims(String token) {

        Claims claims = Jwts.parser()
                .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .parseClaimsJws(token)
                .getBody();

        return claims;
    }

    public String extractEmail(String token) {

        Claims claims = extractAllClaims(token);
        return claims.getSubject();
    }

    public boolean validateToken(String token, String email) {
        String extractedEmail = extractEmail(token);
        return extractedEmail.equals(email) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }



}