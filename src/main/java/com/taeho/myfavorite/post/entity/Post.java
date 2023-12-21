package com.taeho.myfavorite.post.entity;

import com.taeho.myfavorite.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.net.UnknownServiceException;

@Entity
@Table
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Size(max = 500)
    private String title;

    @Size(max = 5000)
    @Column
    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Post(String title, String contents, User user) {
        this.title = title;
        this.contents = contents;
        this.user = user;
    }

    public void updatePost(String title, String contents){
        this.title = title;
        this.contents = contents;
    }
}