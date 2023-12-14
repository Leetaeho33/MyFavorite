package com.taeho.myfavorite.user.controller;


import com.taeho.myfavorite.global.dto.CommonResponseDTO;
import com.taeho.myfavorite.user.dto.CheckUsernameRequestDTO;
import com.taeho.myfavorite.user.dto.SignupRequesetDTO;
import com.taeho.myfavorite.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<CommonResponseDTO> signup(@RequestBody SignupRequesetDTO signupRequesetDTO){
        userService.signup(signupRequesetDTO);
        return ResponseEntity.status(HttpStatus.OK.value()).
                body(CommonResponseDTO.builder().msg("회원가입 성공").status(HttpStatus.OK.value()).build());
    }
    @PostMapping ("/check/username")
    public ResponseEntity<CommonResponseDTO> checkUsername(@RequestBody CheckUsernameRequestDTO checkUsernameRequestDTO){
        userService.checkUsername(checkUsernameRequestDTO);
        return ResponseEntity.status(HttpStatus.OK.value()).
                body(CommonResponseDTO.builder().msg("중복된 아이디가 없습니다. 회원가입 가능합니다.").
                        status(HttpStatus.OK.value()).build());
    }
}
