package com.meta.member.jwt;


import com.meta.member.dto.response.MemberTokenResponseDto;
import com.meta.member.entity.Member;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {
    private static final String EMAIL_KEY = "email";
    private static final String AUTHORITIES_KEY = "auth";
    private static final String BEARER_TYPE = "Bearer";
    
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 30 * 60 * 1000L;            // 30분
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 14 * 24 * 60 * 60 * 1000L;    // 7일

//    private static final long REFRESH_TOKEN_EXPIRE_TIME = 2 * 60 * 1000L;    // 7일
//    private static final long ACCESS_TOKEN_EXPIRE_TIME = 60 * 1000L;          // 30초


    private final Key key;
    private final StringRedisTemplate stringRedisTemplate; // Redis 연동을 위한 객체

    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey, StringRedisTemplate stringRedisTemplate) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.stringRedisTemplate = stringRedisTemplate; // Redis 연동 객체 초기화
    }

    // 유저 정보를 가지고 AccessToken, RefreshToken 을 생성하는 메서드
    public MemberTokenResponseDto generateToken(Authentication authentication, Member member, String platform) {
        long now = (new Date()).getTime();
        // Access Token 생성
        Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        String accessToken = Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS256)
                .setHeaderParam("typ","JWT")
                .setSubject(member.getId().toString())
                .claim(EMAIL_KEY, authentication.getName())
                .claim(AUTHORITIES_KEY, member.getRole())
                .claim("platform", platform)
                .setExpiration(accessTokenExpiresIn)
                .compact();

        // Refresh Token 생성
        String refreshToken = Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS256)
                .setHeaderParam("typ","JWT")
                .setSubject(member.getId().toString())
                .claim(EMAIL_KEY, authentication.getName())
                .claim(AUTHORITIES_KEY, member.getRole())
                .claim("platform", platform)
                .setExpiration(new Date(now + REFRESH_TOKEN_EXPIRE_TIME))
                .compact();


        return MemberTokenResponseDto.builder()
                .memberId(member.getId())
                .nickName(member.getNickName())
                .profileImage(member.getProfileImage())
                .settingImage(member.getSettingImage())
                .grantType(BEARER_TYPE)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .refreshTokenExpirationTime(REFRESH_TOKEN_EXPIRE_TIME)
                .build();
    }


    public Long getExpiration(String accessToken) {
        // accessToken 남은 유효시간
        Date expiration = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody().getExpiration();
        // 현재 시간
        Long now = new Date().getTime();
        return (expiration.getTime() - now);
    }

    public String getMember(String accessToken) {
        checkLength(accessToken);
        String token = accessToken.substring(7);

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public void checkLength(String token){
        if(token.length() < 7) throw new JwtException("올바르지 않은 토큰 유형입니다.");
    }

}
