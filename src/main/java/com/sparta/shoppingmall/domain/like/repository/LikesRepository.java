package com.sparta.shoppingmall.domain.like.repository;

import com.sparta.shoppingmall.domain.comment.entity.Comment;
import com.sparta.shoppingmall.domain.like.entity.ContentType;
import com.sparta.shoppingmall.domain.like.entity.Likes;
import com.sparta.shoppingmall.domain.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long>, LikesRepositoryCustom {
    Optional<Likes> findByContentTypeAndContentId(ContentType contentType, Long contentId);

    @Query("SELECT l FROM Likes l WHERE l.contentType = :contentType AND l.contentId = :contentId AND l.user.id = :userId")
    Optional<Likes> findByContentTypeAndContentIdAndUserId(ContentType contentType, Long contentId, Long userId);

    @Query("SELECT COUNT(l) FROM Likes l WHERE l.user.id = :userId AND l.contentType = 'PRODUCT' AND l.status = 'LIKED'")
    Long countLikedProductsByUserId(@Param("userId") Long userId);

    @Query("SELECT COUNT(l) FROM Likes l WHERE l.user.id = :userId AND l.contentType = 'COMMENT' AND l.status = 'LIKED'")
    Long countLikedCommentsByUserId(@Param("userId") Long userId);
}
