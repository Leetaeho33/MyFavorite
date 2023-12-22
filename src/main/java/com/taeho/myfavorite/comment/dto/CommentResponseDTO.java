package com.taeho.myfavorite.comment.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.taeho.myfavorite.comment.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentResponseDTO {
    String text;
    String author;
    LocalDateTime createdAt;
    LocalDateTime modifiedAt;


    public CommentResponseDTO(Comment comment){
        this.text = comment.getText();
        this.author = comment.getUser().getNickname();
        this.createdAt = LocalDateTime.now();
        this.modifiedAt = comment.getModifiedAt();
    }
}
