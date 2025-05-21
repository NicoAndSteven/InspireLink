package com.coco.inspirelink.utils.jwt;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;

/**
 * @Author: MOHE
 * @Description: TODO
 * @Date: 2025/5/19 下午4:20
 * @Version: 1.0
 */


@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expirationMillis;

    private Key secretKey;

    // 初始化密钥
    @PostConstruct
    public void init() {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    // 生成 JWT
    public String generateToken(String username,int roles) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationMillis);

        return Jwts.builder()
                .setSubject(username)
                .claim("roles",roles)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(secretKey)
                .compact();
    }

    // 解析 Token 获取用户名
    public String getUsernameFromToken(String token) {
        return parseClaims(token).getBody().getSubject();
    }

    // 验证 Token 有效性
    public boolean validateToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (ExpiredJwtException e) {
            System.out.println("Token 过期");
        } catch (UnsupportedJwtException e) {
            System.out.println("Token 不支持");
        } catch (MalformedJwtException e) {
            System.out.println("Token 格式错误");
        } catch (SecurityException | IllegalArgumentException e) {
            System.out.println("Token 安全异常");
        }
        return false;
    }

    // 解析 Token
    private Jws<Claims> parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token);
    }
    public List<String> getAuthoritiesFromToken(String token) {
        return parseClaims(token).getBody().get("roles", List.class);
    }

}
