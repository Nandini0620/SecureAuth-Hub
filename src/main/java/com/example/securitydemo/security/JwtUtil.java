package com.example.securitydemo.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
@Component
public class JwtUtil
{
    @Value("${jwt.secret.key}")
    String secretkey;

    public String generateToken(String email)
    {
        Key key  = Keys.hmacShaKeyFor(secretkey.getBytes(StandardCharsets.UTF_8));

        String token= Jwts.builder().setSubject(email)
                .signWith(key, SignatureAlgorithm.HS256)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 15) )
                .compact();
        return token;
    }

    public String extractToken(String token)
    {
        Key key  = Keys.hmacShaKeyFor(secretkey.getBytes(StandardCharsets.UTF_8));
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().getSubject();
    }
}
