package com.sparta.shoppingmall.domain.like.repository;

import com.sparta.shoppingmall.domain.comment.entity.Comment;
import com.sparta.shoppingmall.domain.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LikesRepositoryCustom {
    Page<Product> findLikedProductsByUserId(Long userId, Pageable pageable);
    Page<Comment> findLikedCommentsByUserId(Long userId, Pageable pageable);
}
