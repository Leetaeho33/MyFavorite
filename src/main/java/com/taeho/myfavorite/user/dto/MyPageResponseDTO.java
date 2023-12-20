package com.taeho.myfavorite.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MyPageResponseDTO {
    String username;
    String nickname;

    @Builder
    public MyPageResponseDTO(String username, String nickname) {
        this.username = username;
        this.nickname = nickname;
    }
}
