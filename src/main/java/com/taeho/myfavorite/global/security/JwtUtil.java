package com.taeho.myfavorite.global.security;

import com.fasterxml.jackson.databind.DatabindException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.Key;
import java.security.KeyStore;
import java.util.Base64;
import java.util.Date;

@Slf4j(topic = "jwt Util")
@Component
public class JwtUtil {
    private final String AUTHORIZATION = "Authorization";
    private final String BEARER = "Bearer ";
    @Value("${jwt.secret.key}")
    private String secretKey;

    // secret key 암호화 알고리즘
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    private Key key;
    Long TOKEN_EXPIRATION_TIME = 60 *60 * 1000L;

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    // 토큰 만들기
    public String createToken(String username) {
        log.info("토큰 생성");
        Date date = new Date();

        // 토큰 만료시간 60분
        return BEARER +
                Jwts.builder()
                        .setSubject(username)   // claims에 정보를 넣어줌(여기서는 유저의 이름)
                        .setExpiration(new Date(date.getTime() + TOKEN_EXPIRATION_TIME))   // 토큰 만료시간
                        .setIssuedAt(date)
                        .signWith(key, signatureAlgorithm)  //토큰 시크릿키 서명 알고리즘
                        .compact();     ///토큰 생성
    }
    // 토큰 유효성 검사
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException e) {
            log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT signature, 손상된 토큰입니다.");
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            log.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        return false;
    }

    // 토큰에서 유저 정보를 뽑아오기. 유저정보는 claims에 들어있으므로 claim를 반환
    public Claims getUserInfoFromToken(String token) {
        log.info("토큰에서 user정보 뽑기");
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    // 토큰 확인 및 자르기
    public String resolveToken(String token) {
        log.info("토큰 확인 및 자르기");
        if (StringUtils.hasText(token) && token.startsWith("Bearer+")) {
            log.info("토큰 확인 완료");
            return token.substring(7);
        }
        return null;
    }
    // 쿠키에 토큰 담기
    public void setCookieAuth(String token, HttpServletResponse response) {
        try {
            log.info("쿠키 생성");
            token = URLEncoder.encode(token, "UTF-8").replaceAll("//+", "%20"); // Cookie Value 에는 공백이 불가능해서 encoding 진행
            Cookie cookie = new Cookie(AUTHORIZATION, token);
            cookie.setPath("/");
            cookie.setMaxAge(60 * 60 * 60);
            cookie.setDomain("localhost");
            response.addCookie(cookie);
            log.info("쿠키 생성 완료");
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage());
        }
    }
    public String getTokenFromRequest(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if(cookies!=null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals(AUTHORIZATION)){
                    try{
                        log.info("쿠키에서 토큰 가져왔음");
                        log.info(URLDecoder.decode(cookie.getValue(), "UTF-8"));
                        return URLDecoder.decode(cookie.getValue(), "UTF-8");
                    }catch (UnsupportedEncodingException e){
                        return null;
                    }
                }
            }
        }
        return null;
    }
}
