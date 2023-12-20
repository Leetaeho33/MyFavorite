package com.taeho.myfavorite.post.repository;

import com.taeho.myfavorite.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
