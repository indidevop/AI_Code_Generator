package com.springboot.AI_Code_Generator.security;

import com.springboot.AI_Code_Generator.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

@Service
public class AuthUtil {

    @Value("${jwt.secret-key}")
    private String jwtSecret;

    public String generateJWTAccessToken(User user){
        return Jwts.builder()
                .subject(user.getUsername())
                .claim("userId",user.getId().toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+1000*60*10))
                .signWith(encodeSecretKey())
                .compact();
    }

    private SecretKey encodeSecretKey(){
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    public JWTUserPrinciple verifyAccessToken(String token)
    {
        Claims claims = Jwts.parser()
                .verifyWith(encodeSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        Long userId = Long.parseLong(claims.get("userId",String.class));
        String username = claims.getSubject();

        return new JWTUserPrinciple(userId, username, new ArrayList<>());
    }

    public Long getCurrentUserId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication==null || !(authentication.getPrincipal() instanceof JWTUserPrinciple))
        {
            throw new AuthenticationCredentialsNotFoundException("No JWT found in context");
        }

        return ((JWTUserPrinciple) authentication.getPrincipal()).userId();
    }
}
