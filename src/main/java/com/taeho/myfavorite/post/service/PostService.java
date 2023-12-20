package com.taeho.myfavorite.post.service;

import com.taeho.myfavorite.post.dto.PostRequestDTO;
import com.taeho.myfavorite.post.dto.PostResponseDTO;
import com.taeho.myfavorite.post.entity.Post;
import com.taeho.myfavorite.post.repository.PostRepository;
import com.taeho.myfavorite.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.dialect.PostgreSQLJsonPGObjectJsonbType;
import org.springframework.stereotype.Service;

@Service
@Slf4j(topic = "postService")
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public PostResponseDTO post(PostRequestDTO postRequestDTO, User user) {
        Post post = Post.builder().title(postRequestDTO.getTitle()).contents(postRequestDTO.getContents())
                .author(user.getNickname()).user(user).build();
        postRepository.save(post);
        return PostResponseDTO.builder().title(post.getTitle()).
                contents(post.getContents()).author(user.getNickname()).build();
    }

}
