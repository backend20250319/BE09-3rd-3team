package io.studyit.userservice.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    @Value("${jwt.refresh-expiration}")
    private long jwtRefreshExpiration;

    private SecretKey secretKey;
    private boolean initialized = false;

    @PostConstruct
    public void init() {
        try {
            System.out.println("=== JWT 초기화 시작 ===");
            System.out.println("JWT Secret: " + jwtSecret);
            System.out.println("JWT Secret length: " + (jwtSecret != null ? jwtSecret.length() : "null"));

            if (jwtSecret == null || jwtSecret.trim().isEmpty()) {
                throw new IllegalArgumentException("JWT secret이 설정되지 않았습니다.");
            }

            // 개행 문자와 공백 제거
            String cleanedSecret = jwtSecret.replaceAll("\\s+", "");
            System.out.println("Cleaned JWT Secret: " + cleanedSecret);
            System.out.println("Cleaned JWT Secret length: " + cleanedSecret.length());

            byte[] keyBytes = Decoders.BASE64.decode(cleanedSecret);
            System.out.println("Decoded key bytes length: " + keyBytes.length);

            secretKey = Keys.hmacShaKeyFor(keyBytes);
            System.out.println("=== JWT 초기화 성공 ===");

        } catch (Exception e) {
            System.err.println("=== JWT 초기화 실패 ===");
            System.err.println("Error: " + e.getMessage());
            System.err.println("JWT Secret 값: '" + jwtSecret + "'");
            e.printStackTrace();

            // Config Server에서 값을 못 가져왔을 가능성을 대비한 fallback
            System.out.println("기본값으로 재시도...");
            try {
                String fallbackSecret = "bXlTZWNyZXRLZXlGb3JKV1RUb2tlbkdlbmVyYXRpb25BbmRWYWxpZGF0aW9uUHVycG9zZXM=";
                byte[] keyBytes = Decoders.BASE64.decode(fallbackSecret);
                secretKey = Keys.hmacShaKeyFor(keyBytes);
                System.out.println("기본값으로 JWT 초기화 성공");
            } catch (Exception fallbackError) {
                throw new RuntimeException("JWT 초기화 완전 실패", fallbackError);
            }
        }
    }

    public String createToken(String username, String role, Long userId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpiration);
        return Jwts.builder()
                .subject(username)
                .claim("role", role)
                .claim("userId", userId)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(secretKey)
                .compact();
    }

    public String createRefreshToken(String username, String role, Long userId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtRefreshExpiration);
        return Jwts.builder()
                .subject(username)
                .claim("role", role)
                .claim("userId", userId)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(secretKey)
                .compact();
    }

    public long getRefreshExpiration() {
        return jwtRefreshExpiration;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            throw new BadCredentialsException("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            throw new BadCredentialsException("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            throw new BadCredentialsException("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            throw new BadCredentialsException("JWT Token claims empty", e);
        }

    }

    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }

}
