package com.taeho.myfavorite.user.controller;


import com.taeho.myfavorite.global.security.JwtUtil;
import com.taeho.myfavorite.global.security.userdetails.UserDetailsImpl;
import com.taeho.myfavorite.user.dto.CheckUsernameRequestDTO;
import com.taeho.myfavorite.user.dto.LoginRequestDTO;
import com.taeho.myfavorite.user.dto.MyPageResponseDTO;
import com.taeho.myfavorite.user.dto.SignupRequestDTO;
import com.taeho.myfavorite.user.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.net.http.HttpResponse;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<Object> signup(@Validated @RequestBody SignupRequestDTO signupRequesetDTO){
        userService.signup(signupRequesetDTO);
        return new ResponseEntity<>("회원가입 성공", HttpStatus.OK);
    }
    @PostMapping ("/check/username")
    public ResponseEntity<Object> checkUsername(@RequestBody CheckUsernameRequestDTO checkUsernameRequestDTO){
        userService.checkUsername(checkUsernameRequestDTO);
        return new ResponseEntity<>("중복된 아이디가 없습니다. 회원가입 가능합니다.", HttpStatus.OK);
    }
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequestDTO loginRequestDTO,
                                        HttpServletResponse response) throws UnsupportedEncodingException {
        userService.login(loginRequestDTO);
        String token = URLEncoder.encode(jwtUtil.createToken(loginRequestDTO.getUsername()),"UTF-8");
        jwtUtil.setCookieAuth(token, response);
        return new ResponseEntity<>("로그인 완료", HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<Object> getMypage(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getMyPage(userDetails.getUser()));
    }
}
