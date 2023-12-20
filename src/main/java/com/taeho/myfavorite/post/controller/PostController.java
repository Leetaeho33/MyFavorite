package com.taeho.myfavorite.post.controller;

import com.taeho.myfavorite.global.security.userdetails.UserDetailsImpl;
import com.taeho.myfavorite.post.dto.PostRequestDTO;
import com.taeho.myfavorite.post.dto.PostResponseDTO;
import com.taeho.myfavorite.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j(topic = "PostController")
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;
    @PostMapping
    public ResponseEntity<Object> post(@RequestBody PostRequestDTO postRequestDTO,
                                       @AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseEntity.status(HttpStatus.OK).body(postService.post(postRequestDTO, userDetails.getUser()));
    }
}
