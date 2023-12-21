package com.taeho.myfavorite.global.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taeho.myfavorite.global.security.JwtUtil;
import com.taeho.myfavorite.global.security.userdetails.UserDetailsImpl;
import com.taeho.myfavorite.global.security.userdetails.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.net.CookiePolicy;
import java.util.Objects;
import java.util.zip.CheckedOutputStream;

@Slf4j(topic = "JWT 검증/인가")
@RequiredArgsConstructor
public class AuthorizationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        log.info("인증 필터 : 들어옴");
        String token = jwtUtil.getTokenFromRequest(request);
        log.info("인증 필터 : 요청에서 토큰값 가져옴");
        if(Objects.nonNull(token)) {
            log.info("인증 필터 : 토큰값 존재");
            token = jwtUtil.resolveToken(token);
            if(jwtUtil.validateToken(token)) {
                log.info("인증 필터 : 토큰에서 유저 정보 가져옴");
                Claims info = jwtUtil.getUserInfoFromToken(token);

                // 인증정보에 요저정보(username) 넣기
                // username -> user 조회
                String username = info.getSubject();
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                // -> userDetails 에 담고
                UserDetailsImpl userDetails = userDetailsService.getUserDetails(username);
                // -> authentication의 principal 에 담고
                Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                // -> securityContent 에 담고
                context.setAuthentication(authentication);
                // -> SecurityContextHolder 에 담고
                SecurityContextHolder.setContext(context);
                // -> 이제 @AuthenticationPrincipal 로 조회할 수 있음
            } else {
                // 인증정보가 존재하지 않을 때
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setContentType("application/json; charset=UTF-8");
                response.getWriter().write(objectMapper.writeValueAsString(
                        new ResponseEntity<>("토큰 정보가 유호하지 않습니다.", HttpStatus.BAD_REQUEST)));
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}
