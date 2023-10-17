package com.meta.apigateway.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.*;

@Slf4j
@Component
public class JwtTokenProvider {



    @Value("${jwt.secret}")
    private String uniqueKey;

//    private int accessTokenValidTime = 1000 * 60 * 90; // AccessToken 유효시간. (단위: ms) DEFAULT: 90분
//    private int refreshTokenValidTime = 1000 * 60 * 60 * 12; // RefreshToken 유효시간. (단위: ms) DEFAULT: 12시간


    // 유저 정보를 토대로 AccessToken, RefreshToken을 생성하는 메서드
    private static final String EMAIL_KEY = "email";
    private static final String NAME_KEY = "name";
    private static final String AUTHORITIES_KEY = "auth";
    private static final String BEARER_TYPE = "Bearer";
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 30 * 60 * 1000L;            // 30분
    private static final long REFRESH_TOKEN_EXPIRE_TIME  = 14 * 24 * 60 * 60 * 1000L;   // 14일
//    private static final long ACCESS_TOKEN_EXPIRE_TIME = 60 * 1000L;            // 30초
//    private static final long REFRESH_TOKEN_EXPIRE_TIME = 2 * 60 * 1000L;    // 2분


    private final Key key;

    private static final String AUTHORIZATION_HEADER = "Authorization";


    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey) {
        System.out.println(secretKey);
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // 유저 정보를 가지고 AccessToken, RefreshToken 을 생성하는 메서드
    public String generateToken(Map<String,Object> payload) {
        long now = (new Date()).getTime();
        // Access Token 생성

        Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        String accessToken = Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS256)
                .setHeaderParam("typ", "JWT")
                .setSubject((String) payload.get("sub"))
                .claim(EMAIL_KEY, payload.get(EMAIL_KEY))
                .claim(AUTHORITIES_KEY, payload.get(AUTHORITIES_KEY))
                .claim("platform", payload.get("platform"))
                .setExpiration(accessTokenExpiresIn)
                .compact();

        return accessToken;
    }

    //    public String generateRefreshToken(Map<String,Object> payload) {
//        long now = (new Date()).getTime();
//
//        Date refreshTokenExpiresIn = new Date(now + REFRESH_TOKEN_EXPIRE_TIME);
//        String refreshToken = Jwts.builder()
//                .signWith(key, SignatureAlgorithm.HS256)
//                .setHeaderParam("typ", "JWT")
//                .setSubject((String) payload.get("sub"))
//                .claim(EMAIL_KEY, payload.get(EMAIL_KEY))
//                .claim(AUTHORITIES_KEY, payload.get(AUTHORITIES_KEY))
//                .claim("platform", payload.get("platform"))
//                .setExpiration(refreshTokenExpiresIn)
//                .compact();
//
//        return refreshToken;
//    }

    public String resolveToken(ServerHttpRequest request) {
        HttpHeaders requestHeader = request.getHeaders();
        String accessToken = requestHeader.get(AUTHORIZATION_HEADER).get(0);
        System.out.println("[resolveToken@JwtTokenProvider]" + accessToken);
        if (StringUtils.hasText(accessToken) && accessToken.startsWith(BEARER_TYPE)) {
            return accessToken.substring(7);
        }
        return null;
    }

    // 토큰 정보를 검증하는 메서드
    public boolean validateToken(String token,String tokenType) {
        log.info("apigateway안에서 validate 안으로 들어옴");
        System.out.println(tokenType +" "+token);
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(uniqueKey).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (SecurityException | MalformedJwtException e) {
            log.info("유효하지 않은 Token !! -> " + token);
        } catch (ExpiredJwtException e) {
            log.info("만료된 Token !! -> " + token);
        } catch (UnsupportedJwtException e) {
            log.info("지원하지 않는 형식의 Token !! -> " + token);
        } catch (IllegalArgumentException e) {
            log.info("Token이 빈 문자열을 반환하였습니다 !! -> " + token);
        }
        return false;
    }

}