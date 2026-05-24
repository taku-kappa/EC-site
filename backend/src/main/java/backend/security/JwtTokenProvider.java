package backend.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration-ms}")
    private long jwtExpirationInMs;

    private Key key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(
                jwtSecret.getBytes(StandardCharsets.UTF_8)
        );
    }

    // JWT生成
    public String generateToken(Authentication authentication) {

        CustomUserDetails userPrincipal =
                (CustomUserDetails) authentication.getPrincipal();

        Date now = new Date();

        Date expiryDate =
                new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .claim("userId", userPrincipal.getId())
                .claim("role",
                        userPrincipal.getAuthorities()
                                .iterator()
                                .next()
                                .getAuthority())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // Email取得
    public String getUsernameFromJWT(String token) {
        return getClaims(token).getSubject();
    }

    // userId取得
    public Long getUserIdFromJWT(String token) {
        return getClaims(token).get("userId", Long.class);
    }

    // claims共通取得
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // JWT検証
    public boolean validateToken(String token) {

        try {
            getClaims(token);
            return true;

        } catch (SecurityException e) {
            log.error("Invalid JWT signature");

        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token");

        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token");

        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token");

        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty");
        }

        return false;
    }
}