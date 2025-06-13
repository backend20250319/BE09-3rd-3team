package io.studyit.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;

public class JwtTokenProvider {
    private final SecretKey secretKey;
    @Getter private final long expiration;
    @Getter private final long refreshExpiration;

    public JwtTokenProvider(JwtProperties properties) {
        byte[] bytes = Decoders.BASE64.decode(properties.getSecret());
        this.secretKey = Keys.hmacShaKeyFor(bytes);
        this.expiration = properties.getExpiration();
        this.refreshExpiration = properties.getRefreshExpiration();
    }

    private Jws<Claims> parseToken(String token) throws InvalidJwtException {
        try {
            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);
        } catch (SecurityException | MalformedJwtException e) {
            throw new InvalidJwtException("Invalid JWT Token");
        } catch (ExpiredJwtException e) {
            throw new InvalidJwtException("Expired JWT Token");
        } catch (UnsupportedJwtException e) {
            throw new InvalidJwtException("Unsupported JWT Token");
        } catch (IllegalArgumentException e) {
            throw new InvalidJwtException("JWT Token claims empty");
        }
    }

    public String createToken(String subject) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .subject(subject)
                .issuedAt(now)
                .expiration(expiry)
                .signWith(secretKey)
                .compact();
    }

    public String createRefreshToken(String subject) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + refreshExpiration);
        return Jwts.builder()
                .subject(subject)
                .issuedAt(now)
                .expiration(expiry)
                .signWith(secretKey)
                .compact();
    }

    public boolean isValidToken(String token) {
        try {
            parseToken(token);
            return true;
        } catch (InvalidJwtException e) {
            return false;
        }
    }

    public JwtPayload getPayloadFromToken(String token) throws InvalidJwtException {
        var payload = parseToken(token).getPayload();
        return new JwtPayload(payload.getSubject());
    }
}
