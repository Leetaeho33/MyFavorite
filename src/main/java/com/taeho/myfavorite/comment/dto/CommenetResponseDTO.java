package com.taeho.myfavorite.comment.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.taeho.myfavorite.comment.entity.Comment;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommenetResponseDTO {
    String text;
    String author;
    LocalDateTime createdAt;
    LocalDateTime modifiedAt;


    public CommenetResponseDTO(Comment comment){
        this.text = comment.getText();
        this.author = comment.getUser().getNickname();
        this.createdAt = LocalDateTime.now();
    }
}
