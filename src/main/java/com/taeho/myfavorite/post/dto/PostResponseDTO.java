package com.taeho.myfavorite.post.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.taeho.myfavorite.post.entity.Post;
import lombok.Builder;
import lombok.Getter;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostResponseDTO {
    String title;
    String contents;
    String author;
    LocalDateTime createdAt;
    LocalDateTime modifiedAt;

    @Builder
    public PostResponseDTO(String title, String contents, String author, Post post) {
        this.title = title;
        this.contents = contents;
        this.author = author;
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
    }
}
