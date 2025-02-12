package com.crioprogram.stayease.utility;


import com.crioprogram.stayease.model.User;
import com.crioprogram.stayease.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JWTService {

    @Autowired
    private UserRepository userRepository;

    private static final String SECRET_KEY = "YourSuperSecretKeyForJWTGeneration";
//    private static final long EXPIRATION_TIME = 86400000; // 1 day

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String getToken(String email, String role) {
        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String getEmailFromToken(String token){
        return extractClaims(token).getSubject();
    }

    public Claims extractClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
    }

    public String getRoleFromToken(String token){
        return extractClaims(token).get("role", String.class);
    }

    public boolean validateToken(String token){
        try {
            extractClaims(token);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public int getUserIdFromToken(String token){
        token = token.substring(7);
        String emailFromToken = getEmailFromToken(token);
        User user = userRepository.findByEmailId(emailFromToken);
        System.out.println("user from Token : "+ user );
        if (user == null){
            throw new IllegalStateException("User Not Found");
        }
        return user.getUserId();
    }

}
