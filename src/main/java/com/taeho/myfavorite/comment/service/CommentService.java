package com.taeho.myfavorite.comment.service;

import com.taeho.myfavorite.comment.dto.CommenetResponseDTO;
import com.taeho.myfavorite.comment.dto.CommentRequestDTO;
import com.taeho.myfavorite.comment.entity.Comment;
import com.taeho.myfavorite.comment.repository.CommentRepository;
import com.taeho.myfavorite.post.entity.Post;
import com.taeho.myfavorite.post.service.PostService;
import com.taeho.myfavorite.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "CommentService")
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostService postService;
    public CommenetResponseDTO post(CommentRequestDTO requestDTO, User user, Long postId) {
        log.info("댓글 저장 시작");
        Post post = postService.findPostById(postId);
        Comment comment = new Comment(requestDTO, user, post);
        commentRepository.save(comment);
        log.info("댓글 저장 완료");
        return new CommenetResponseDTO(comment);
    }
}
