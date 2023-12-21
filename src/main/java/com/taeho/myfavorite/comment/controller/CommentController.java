package com.taeho.myfavorite.comment.controller;

import com.taeho.myfavorite.comment.dto.CommentRequestDTO;
import com.taeho.myfavorite.comment.service.CommentService;
import com.taeho.myfavorite.global.security.userdetails.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/post/{postId}")
    public ResponseEntity<Object> post(@RequestBody CommentRequestDTO requestDTO,
                                       @AuthenticationPrincipal UserDetailsImpl userDetails,
                                       @PathVariable Long postId){
        return ResponseEntity.status(HttpStatus.OK).
                body(commentService.post(requestDTO, userDetails.getUser(), postId));
    }
    @PutMapping("/comment/{commentId}")
    public ResponseEntity<Object> update(@RequestBody CommentRequestDTO requestDTO,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails,
                                         @PathVariable Long commentId){
        return ResponseEntity.status(HttpStatus.OK).
                body(commentService.update(requestDTO, userDetails.getUser(), commentId));
    }
    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<Object> delete(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                         @PathVariable Long commentId){
        commentService.delete(userDetails.getUser(), commentId);
        return ResponseEntity.status(HttpStatus.OK).
                body("댓글 삭제 완료");
    }
}
