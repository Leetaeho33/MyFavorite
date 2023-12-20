package com.taeho.myfavorite.post.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostResponseDTO {
    String title;
    String contents;
    String author;

    @Builder
    public PostResponseDTO(String title, String contents, String author) {
        this.title = title;
        this.contents = contents;
        this.author = author;
    }
}
