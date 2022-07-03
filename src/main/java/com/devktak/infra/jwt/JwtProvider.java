package com.devktak.infra.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtProvider implements InitializingBean {

    // base64로 인코딩 되어 있는 Secret key 사용 (HS512 알고리즘을 사용할 것이기 때문에 64Byte 이상 이어야 함)
    private final String secret;
    private final long tokenValidityInSeconds; // 토큰 만료시간

    private Key key;

    public JwtProvider(@Value("${spring.jwt.secret}") String secret,
                       @Value("${spring.jwt.token-validity-in-seconds}") long tokenValidityInSeconds) {
        this.secret = secret;
        this.tokenValidityInSeconds = tokenValidityInSeconds;
    }

    // BeanFactory에 의해 모든 property 가 설정되고 난 뒤 실행되는 메소드
    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = Decoders.BASE64.decode(secret); // 디코딩
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(String subject) {
        if (tokenValidityInSeconds <= 0) {
            throw new RuntimeException("JWT 토큰의 만료시간은 0 보다 커야합니다.");
        }

        return Jwts.builder()
                .setSubject(subject)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(new Date(System.currentTimeMillis() + tokenValidityInSeconds))
                .compact();
    }

    public String getSubject(String token) {
        Claims claims = Jwts // 페이로드 내에 담기는 정보
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token) // token을 넣어서 풀어줌
                .getBody();

        return claims.getSubject();
    }
}
