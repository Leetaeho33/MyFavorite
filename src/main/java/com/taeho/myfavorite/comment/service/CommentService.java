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
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public CommenetResponseDTO update(CommentRequestDTO requestDTO, User user, Long commentId) {
        log.info("댓글 수정 시작");
        Comment comment = findCommentById(commentId);
        if(checkAuthorization(user, comment)){
            comment.updateComment(requestDTO);
        }
        log.info("댓글 수정 완료");
        return new CommenetResponseDTO(comment);
    }

    @Transactional
    public void delete(User user, Long commentId) {
        log.info("댓글 삭제 시작");
        Comment comment = findCommentById(commentId);
        if(checkAuthorization(user, comment)) {
            commentRepository.delete(comment);
            log.info("댓글 삭제 완료");
        }
    }
    private Comment findCommentById(Long commentId){
        log.info("댓글 조회 시작");
        return commentRepository.findById(commentId).orElseThrow(()->
                new IllegalArgumentException("해당 댓글은 존재하지 않스니다."));
    }
    private boolean checkAuthorization(User user, Comment comment){
        log.info("작성자 인가 확인");
        if(comment.getUser().getUsername().equals(user.getUsername())){
            return true;
        }else throw new IllegalArgumentException("작성자만 접근할 수 있습니다.");
    }
}
