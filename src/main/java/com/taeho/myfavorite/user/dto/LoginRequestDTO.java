package com.taeho.myfavorite.user.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class LoginRequestDTO {

    @NotEmpty(message = "아이디는 필수 입력값입니다")
    @Pattern(regexp = "^[a-z0-9]{5,20}$", message = "아이디는 소문자와 숫자로 이루어진 4글자~10글자 사이여야 합니다.")
    String username;

    @NotEmpty(message = "비밀번호는 필수 입력값입니다")
    @Pattern(regexp = "^[a-zA-Z0-9]{4,10}$", message = "비밀번호는 소문자,대문자와 숫자로 이루어진 4글자~10글자 사이여야 합니다.")
    String password;
}
