package com.taeho.myfavorite.global.security;

import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;

import java.security.Key;

public class JwtUtil {
    private final String AUTHORIZATION = "Authorization";
    private final String BEARER = "Bearer ";
    @Value("${jwt.secret.key}")
    private String secretKey;
    private Key key;
//    private SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;


    // 토큰 만들기

    // 토큰 검증

    // 토큰 확인 및 자르기

    // 토큰에 정보 담기

    // 쿠키에 토큰 담기
}
