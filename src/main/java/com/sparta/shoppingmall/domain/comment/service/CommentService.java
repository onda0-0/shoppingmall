package com.sparta.shoppingmall.domain.comment.service;

import com.sparta.shoppingmall.common.exception.customexception.CommentNotFoundException;
import com.sparta.shoppingmall.common.util.PageUtil;
import com.sparta.shoppingmall.domain.comment.dto.CommentRequest;
import com.sparta.shoppingmall.domain.comment.dto.CommentResponse;
import com.sparta.shoppingmall.domain.comment.entity.Comment;
import com.sparta.shoppingmall.domain.comment.repository.CommentRepository;
import com.sparta.shoppingmall.domain.like.repository.LikesRepository;
import com.sparta.shoppingmall.domain.like.service.LikesService;
import com.sparta.shoppingmall.domain.product.entity.Product;
import com.sparta.shoppingmall.domain.product.service.ProductService;
import com.sparta.shoppingmall.domain.user.entity.User;
import com.sparta.shoppingmall.domain.user.entity.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final ProductService productService;
    private final CommentRepository commentRepository;
    private final LikesRepository likesRepository;
    //private final LikesService likesService;

    /**
     * 댓글 등록
     */
    @Transactional
    public CommentResponse createComment(CommentRequest request, Long productId, User user) {
        Product product = productService.findByProductId(productId);
        Comment comment = Comment.createComment(request, user, product);

        commentRepository.save(comment);

        return CommentResponse.of(comment);
    }

    /**
     * 해당 상품의 댓글 전체 조회
     */
    @Transactional(readOnly = true)
    public List<CommentResponse> getComments(Long productId) {
        Product product = productService.findByProductId(productId);
        List<Comment> comments = product.getComments();

        /*List<CommentResponse> response = new ArrayList<>();
        for(Comment comment : comments) {
            response.add(new CommentResponse(comment));
        }

        return response;*/
        return comments.stream()
                .map(CommentResponse::of)
                .collect(Collectors.toList());
    }


    /**
     * 댓글 수정
     */
    @Transactional
    public CommentResponse updateComments(CommentRequest request, Long productId, Long commentId, User user) {
        Comment comment = getComment(commentId);

        if(!user.getUserType().equals(UserType.ADMIN)){ // 관리자가 아닐 경우 댓글 작성자와 로그인 사용자를 비교
            comment.verifyCommentUser(user.getId());
        }
        comment.verifyCommentProduct(productId);
        comment.updateComment(request);

        return CommentResponse.of(comment);
    }

    /**
     * 댓글 삭제
     */
    @Transactional
    public Long deleteComment(Long productId, Long commentId, User user) {
        Comment comment = getComment(commentId);

        if(!user.getUserType().equals(UserType.ADMIN)){ // 관리자가 아닐 경우 댓글 작성자와 로그인 사용자를 비교
            comment.verifyCommentUser(user.getId());
        }
        comment.verifyCommentProduct(productId);
        commentRepository.delete(comment);

        return comment.getId();
    }

    /**
     * 댓글 아이디로 댓글 조회
     */
    public Comment getComment(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(
                () -> new CommentNotFoundException("해당 댓글은 존재하지 않습니다.")
        );
    }

    /**
     * 사용자가 좋아요 한 댓글 조회
     */
    @Transactional(readOnly = true)
    public List<CommentResponse> getLikedCommentsByUser(Long userId, final Integer pageNum, final Boolean isDesc) {
        Pageable pageable = PageUtil.createPageable(pageNum, PageUtil.PAGE_SIZE_FIVE, isDesc);
        Page<Comment> comments = likesRepository.findLikedCommentsByUserId(userId, pageable);
        PageUtil.validateAndSummarizePage(pageNum, comments);
        return comments.stream()
                .map(CommentResponse::of)
                .collect(Collectors.toList());
    }
    /*@Transactional(readOnly = true)
    public List<CommentResponse> getLikedCommentsByUser(Long userId, Integer pageNum, Boolean isDesc) {
        return likesService.getLikedCommentsByUser(userId, pageNum, isDesc);
    }*/


    /*@Transactional(readOnly = true)
    public List<CommentResponse> getLikedCommentsByUser(Long userId,final Integer pageNum, final Boolean isDesc) {
        Pageable pageable = PageUtil.createPageable(pageNum, PageUtil.PAGE_SIZE_FIVE, isDesc);
        Page<Comment> comments = commentRepository.findLikedCommentByUserId(userId,pageable);

        List<CommentResponse> commentResponses = new ArrayList<>();
        for (Comment comment : comments) {
            CommentResponse commentResponse = ProductResponse.of(comment);
            commentResponses.add(commentResponse);
        }
        return commentResponses;
    }*/

}
