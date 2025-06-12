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
        // 중복 초기화 방지
        if (initialized) {
            System.out.println("JWT 이미 초기화됨 - 스킵");
            return;
        }

        try {
            System.out.println("=== JWT 초기화 시작 ===");
            System.out.println("Bean 해시코드: " + this.hashCode());

            if (jwtSecret == null || jwtSecret.trim().isEmpty()) {
                throw new IllegalArgumentException("JWT secret이 설정되지 않았습니다.");
            }

            // 핵심: 모든 공백 문자 제거 (줄바꿈 \r\n, 탭, 공백 등)
            String cleanSecret = jwtSecret.replaceAll("\\s", "");

            System.out.println("원본 JWT Secret length: " + jwtSecret.length());
            System.out.println("정리된 JWT Secret length: " + cleanSecret.length());

            // Base64 디코딩
            byte[] keyBytes = Decoders.BASE64.decode(cleanSecret);
            System.out.println("Decoded key bytes length: " + keyBytes.length);

            // SecretKey 생성
            secretKey = Keys.hmacShaKeyFor(keyBytes);
            initialized = true; // 성공 후 플래그 설정

            System.out.println("=== JWT 초기화 성공 ===");

        } catch (Exception e) {
            System.err.println("=== JWT 초기화 실패 ===");
            System.err.println("Error: " + e.getMessage());
            System.err.println("원본 JWT Secret: '" + jwtSecret + "'");

            // 디버깅: 문제가 되는 문자들 확인
            if (jwtSecret != null) {
                System.err.println("JWT Secret 바이트 분석:");
                for (int i = 0; i < Math.min(jwtSecret.length(), 100); i++) {
                    char c = jwtSecret.charAt(i);
                    if (c == '\r') System.err.println("위치 " + i + ": \\r");
                    else if (c == '\n') System.err.println("위치 " + i + ": \\n");
                    else if (c == '\t') System.err.println("위치 " + i + ": \\t");
                    else if (c == ' ') System.err.println("위치 " + i + ": 공백");
                }
            }

            // Config Server에서 값을 못 가져왔을 가능성을 대비한 fallback
            System.out.println("기본값으로 재시도...");
            try {
                String fallbackSecret = "bXlTZWNyZXRLZXlGb3JKV1RUb2tlbkdlbmVyYXRpb25BbmRWYWxpZGF0aW9uUHVycG9zZXM=";
                byte[] keyBytes = Decoders.BASE64.decode(fallbackSecret);
                secretKey = Keys.hmacShaKeyFor(keyBytes);
                initialized = true; // 성공 후 플래그 설정
                System.out.println("기본값으로 JWT 초기화 성공");
            } catch (Exception fallbackError) {
                System.err.println("기본값으로도 실패: " + fallbackError.getMessage());
                throw new RuntimeException("JWT 초기화 완전 실패", fallbackError);
            }
        }
    }

    // access token 생성 메소드 (claim에 userId 추가)
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

    // refresh token 생성 메소드 (claim에 userId 추가)
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
