package com.sparta.shoppingmall.domain.like.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.shoppingmall.domain.comment.entity.Comment;
import com.sparta.shoppingmall.domain.comment.entity.QComment;
import com.sparta.shoppingmall.domain.like.entity.ContentType;
import com.sparta.shoppingmall.domain.like.entity.LikeStatus;
import com.sparta.shoppingmall.domain.like.entity.QLikes;
import com.sparta.shoppingmall.domain.product.entity.Product;
import com.sparta.shoppingmall.domain.product.entity.QProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;



import java.util.List;

public class LikesRepositoryImpl implements LikesRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Autowired
    public LikesRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<Product> findLikedProductsByUserId(Long userId, Pageable pageable) {
        QLikes likes = QLikes.likes;
        QProduct product = QProduct.product;

        JPAQuery<Product> query = queryFactory
                .select(product)
                .from(likes)
                .innerJoin(product).on(product.id.eq(likes.contentId))
                .where(
                        likes.user.id.eq(userId)
                                .and(likes.contentType.eq(ContentType.PRODUCT))
                                .and(likes.status.eq(LikeStatus.LIKED))
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        List<Product> posts = query.fetch();
        long total = query.fetchCount();

        return new PageImpl<>(posts, pageable, total);
    }


    @Override
    public Page<Comment> findLikedCommentsByUserId(Long userId, Pageable pageable) {
        QLikes likes = QLikes.likes;
        QComment comment = QComment.comment;

        JPAQuery<Comment> query = queryFactory
                .select(comment)
                .from(likes)
                .innerJoin(comment).on(comment.id.eq(likes.contentId))
                .where(
                        likes.user.id.eq(userId)
                                .and(likes.contentType.eq(ContentType.COMMENT))
                                .and(likes.status.eq(LikeStatus.LIKED))
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        List<Comment> comments = query.fetch();
        long total = query.fetchCount();

        return new PageImpl<>(comments, pageable, total);
    }
}
