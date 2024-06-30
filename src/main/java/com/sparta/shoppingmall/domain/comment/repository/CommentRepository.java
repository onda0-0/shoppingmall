package com.sparta.shoppingmall.domain.comment.repository;

import com.sparta.shoppingmall.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {



}
