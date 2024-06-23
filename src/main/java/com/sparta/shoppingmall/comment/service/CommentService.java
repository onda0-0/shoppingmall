package com.sparta.shoppingmall.comment.service;

import com.sparta.shoppingmall.comment.dto.CommentRequest;
import com.sparta.shoppingmall.comment.dto.CommentResponse;
import com.sparta.shoppingmall.comment.entity.Comment;
import com.sparta.shoppingmall.comment.repository.CommentRepository;
import com.sparta.shoppingmall.product.entity.Product;
import com.sparta.shoppingmall.product.service.ProductService;
import com.sparta.shoppingmall.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final ProductService productService;
    private final CommentRepository commentRepository;

    /**
     * 댓글 등록
     */
    @Transactional
    public CommentResponse createComment(CommentRequest request, Long productId, User user) {
        //해당 상품 조회
        Product product = productService.findByProductId(productId);
        //상품에 댓글 생성
        Comment comment = new Comment(request, user, product);

        commentRepository.save(comment);

        return new CommentResponse(comment);
    }

}
