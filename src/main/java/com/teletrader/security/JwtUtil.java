package com.teletrader.security;


import com.teletrader.entity.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Component
public class JwtUtil {

    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final Set<String> invalidatedTokens = new HashSet<>();


    public static String getSecretKeyBase64() {
        return Base64.getEncoder().encodeToString(SECRET_KEY.getEncoded());
    }
    private static final long EXPIRATION_TIME = 86400000; // 1 dan

    public String generateToken(String username, Role role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role.name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    public boolean isTokenInvalid(String token) {
        return invalidatedTokens.contains(token);
    }


    public void invalidateToken(String token) {
        invalidatedTokens.add(token);
    }
    public boolean validateToken(String token) {
        try {
            return !isTokenInvalid(token) &&
                    getClaims(token).getExpiration().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }
    public Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
}