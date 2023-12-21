package com.taeho.myfavorite.comment.entity;

import com.taeho.myfavorite.comment.dto.CommentRequestDTO;
import com.taeho.myfavorite.post.entity.Post;
import com.taeho.myfavorite.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table
@Getter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @Size(max = 500)
    private String text;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime modifiedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    Post post;


    public Comment(CommentRequestDTO commentRequestDTO, User user, Post post) {
        this.text = commentRequestDTO.getText();
        this.user = user;
        this.post = post;
        this.createdAt = LocalDateTime.now();
    }
}
