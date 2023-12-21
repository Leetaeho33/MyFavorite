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
import org.springframework.web.bind.annotation.*;

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
    @PutMapping("/{postId}")
    public ResponseEntity<Object> update(@RequestBody PostRequestDTO postRequestDTO,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails,
                                         @PathVariable Long postId){
        return ResponseEntity.status(HttpStatus.OK).body(postService.update(postRequestDTO, userDetails.getUser(), postId));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Object> delete(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                         @PathVariable Long postId){
        postService.delete(userDetails.getUser(), postId);
        return ResponseEntity.status(HttpStatus.OK).body("게시글이 삭제되었습니다.");
    }

    @GetMapping
    public ResponseEntity<Object> getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(postService.getAll());
    }
}
