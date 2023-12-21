package com.taeho.myfavorite.comment.repository;

import com.taeho.myfavorite.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
