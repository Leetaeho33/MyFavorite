package com.taeho.myfavorite.user.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class SignupRequestDTO {

    @NotEmpty(message = "아이디는 필수 입력값입니다")
    @Pattern(regexp = "^[a-z0-9]{5,20}$", message = "아이디는 소문자와 숫자로 이루어진 4글자~10글자 사이여야 합니다.")
    private String username;

    @NotEmpty(message = "비밀번호는 필수 입력값입니다")
    @Pattern(regexp = "^[a-zA-Z0-9]{4,10}$", message = "비밀번호는 소문자,대문자와 숫자로 이루어진 4글자~10글자 사이여야 합니다.")
    private String password;

    @NotEmpty(message = "닉네임은 필수 입력값입니다")
    @Pattern(regexp = "^[a-zA-Z0-9가-힣]{4,20}$", message = "닉네임은 특수문자 사용 불가능한 4글자~20글자 사이입니다." )
    private String nickname;
}
