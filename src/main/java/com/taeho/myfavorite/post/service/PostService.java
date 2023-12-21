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
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j(topic = "postService")
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public PostResponseDTO post(PostRequestDTO postRequestDTO, User user) {
        Post post = Post.builder().title(postRequestDTO.getTitle()).contents(postRequestDTO.getContents())
                .user(user).build();
        postRepository.save(post);
        log.info("게시글 저장 성공");
        return PostResponseDTO.builder().title(post.getTitle()).
                contents(post.getContents()).author(user.getNickname()).post(post).build();
    }

    @Transactional
    public PostResponseDTO update(PostRequestDTO postRequestDTO, User user, Long postId) {
        Post post = checkAuthorization(user.getUsername(), postId);
        post.updatePost(postRequestDTO.getTitle(), postRequestDTO.getContents());
        log.info("게시글 업데이트 완료");
        return PostResponseDTO.builder().title(post.getTitle()).
                contents(post.getContents()).author(user.getNickname()).post(post).build();
    }

    @Transactional
    public void delete(User user, Long postId) {
        Post post = checkAuthorization(user.getUsername(), postId);
        postRepository.delete(post);
        log.info("게시글 삭제 완료");
    }


    private Post findPostById(Long postId){
        log.info("게시물 검색");
        return postRepository.findById(postId).orElseThrow(
                ()-> new IllegalArgumentException("해당 게시물은 존재하지 않습니다."));
    }

    private boolean checkAuthor(String username, Long postId){
        log.info("작성자 확인");
        if(findPostById(postId).getUser().getUsername().equals(username)){
            return true;
        }else throw new IllegalArgumentException("작성자만 접근할 수 있습니다.");
    }

    private Post checkAuthorization(String username, Long postId){
        Post post = findPostById(postId);
        checkAuthor(username, postId);
        log.info("게시글 및 작성자 확인 완료");
        return post;
    }
}

