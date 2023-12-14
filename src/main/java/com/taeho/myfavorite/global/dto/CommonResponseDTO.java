package com.taeho.myfavorite.global.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CommonResponseDTO {
    String msg;
    int status;
    @Builder
    public CommonResponseDTO(String msg, int status) {
        this.msg = msg;
        this.status = status;
    }
}
