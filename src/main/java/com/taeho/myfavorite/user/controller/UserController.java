package com.taeho.myfavorite.user.controller;


import com.taeho.myfavorite.global.dto.CommonResponseDTO;
import com.taeho.myfavorite.user.dto.CheckUsernameRequestDTO;
import com.taeho.myfavorite.user.dto.SignupRequestDTO;
import com.taeho.myfavorite.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

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
}
