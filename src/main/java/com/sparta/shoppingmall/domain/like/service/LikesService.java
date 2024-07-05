package com.sparta.shoppingmall.domain.like.service;

import com.sparta.shoppingmall.common.exception.customexception.LikedOwnContentException;
import com.sparta.shoppingmall.common.util.PageUtil;
import com.sparta.shoppingmall.domain.comment.dto.CommentResponse;
import com.sparta.shoppingmall.domain.comment.entity.Comment;
import com.sparta.shoppingmall.domain.comment.service.CommentService;
import com.sparta.shoppingmall.domain.like.dto.LikesResponse;
import com.sparta.shoppingmall.domain.like.entity.LikeStatus;
import com.sparta.shoppingmall.domain.like.entity.Likes;
import com.sparta.shoppingmall.domain.like.repository.LikesRepository;
import com.sparta.shoppingmall.domain.like.dto.LikesRequest;
import com.sparta.shoppingmall.domain.product.dto.ProductResponse;
import com.sparta.shoppingmall.domain.product.entity.Product;
import com.sparta.shoppingmall.domain.product.service.ProductService;
import com.sparta.shoppingmall.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class LikesService {

    private final ProductService productService;
    private final CommentService commentService;
    private final LikesRepository likesRepository;

    /**
     * 좋아요 토글
     */
    @Transactional
    public LikesResponse toggleLike(LikesRequest request, User user) {
        if (isOwner(request, user)) {
            throw new LikedOwnContentException("본인 게시글에 좋아요를 누를 수 없습니다.");
        }

        //Optional<Likes> existLike = likesRepository.findByContentTypeAndContentId(request.getContentType(), request.getContentId());
        Optional<Likes> existLike=likesRepository.findByContentTypeAndContentIdAndUserId(request.getContentType(),request.getContentId(),user.getId());

        Likes likes = existLike.orElseGet(() -> createLikes(request, user));

        if (likes.getStatus().equals(LikeStatus.CANCELED)) {
            doLike(likes, user.getId());// 취소된 좋아요 이거나 신규 좋아요인 경우 좋아요
        }else {
            cancelLike(likes, user.getId());
        }

        return new LikesResponse(likes);

    }

    private boolean isOwner(LikesRequest request, User user) {
        switch (request.getContentType()) {
            case PRODUCT:
                return productService.findByProductId(request.getContentId()).getUser().getId().equals(user.getId());
            case COMMENT:
                return commentService.getComment(request.getContentId()).getUser().getId().equals(user.getId());
            default:
                throw new IllegalArgumentException("Unknown content type");
        }
    }

    /**
     * 좋아요 생성 (해당 content에 최초 좋아요 실행일 때)
     */
    private Likes createLikes(LikesRequest request, User user) {
        Likes likes = Likes.createLike(request, user);
        likesRepository.save(likes);

        return likes;
    }
    /**
     * 좋아요
     */
    private void doLike(Likes likes, Long userId) {
        likes.doLike(userId);

        Long contentId = likes.getContentId();
        switch (likes.getContentType()){
            case PRODUCT -> productService.findByProductId(contentId).increaseLikeCount();
            case COMMENT -> commentService.getComment(contentId).increaseLikeCount();
        }
    }

    /**
     * 좋아요 취소
     */
    private void cancelLike(Likes likes, Long userId) {
        likes.cancelLike(userId);

        Long contentId = likes.getContentId();
        switch (likes.getContentType()){
            case PRODUCT -> productService.findByProductId(contentId).decreaseLikeCount();
            case COMMENT -> commentService.getComment(contentId).decreaseLikeCount();
        }
    }

    /**
     * 사용자가 좋아요 한 상품 조회
     */
    public List<ProductResponse> getLikedProductsByUser(Long userId, Integer pageNum, Boolean isDesc) {
        Pageable pageable = PageUtil.createPageable(pageNum, PageUtil.PAGE_SIZE_FIVE, isDesc);
        Page<Product> products = likesRepository.findLikedProductsByUserId(userId, pageable);
        PageUtil.validateAndSummarizePage(pageNum, products);

        return products.stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList());
    }

    /**
     * 사용자가 좋아요 한 댓글 조회
     */
    public List<CommentResponse> getLikedCommentsByUser(Long userId, Integer pageNum, Boolean isDesc) {
        Pageable pageable = PageUtil.createPageable(pageNum, PageUtil.PAGE_SIZE_FIVE, isDesc);
        Page<Comment> comments = likesRepository.findLikedCommentsByUserId(userId, pageable);
        PageUtil.validateAndSummarizePage(pageNum, comments);

        return comments.stream()
                .map(CommentResponse::of)
                .collect(Collectors.toList());
    }

}
