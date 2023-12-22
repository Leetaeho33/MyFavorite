package com.taeho.myfavorite.post.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.taeho.myfavorite.comment.dto.CommentResponseDTO;
import com.taeho.myfavorite.post.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostResponseDTO {
    String title;
    String contents;
    String author;
    List<CommentResponseDTO> commentResponseDTOList = new ArrayList<>();
    LocalDateTime createdAt;
    LocalDateTime modifiedAt;


    public PostResponseDTO(Post post, List<CommentResponseDTO> commentResponseDTOList) {
        this.title = post.getTitle();
        this.contents = post.getContents();
        this.author = post.getUser().getNickname();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
        this.commentResponseDTOList = commentResponseDTOList;
    }
    public PostResponseDTO(Post post) {
        this.title = post.getTitle();
        this.contents = post.getContents();
        this.author = post.getUser().getNickname();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
    }
}
