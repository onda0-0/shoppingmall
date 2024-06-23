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

import java.util.ArrayList;
import java.util.List;

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
        Product product = productService.findByProductId(productId);
        Comment comment = new Comment(request, user, product);

        commentRepository.save(comment);

        return new CommentResponse(comment);
    }

    /**
     * 해당 상품의 댓글 전체 조회
     */
    public List<CommentResponse> getComments(Long productId) {
        Product product = productService.findByProductId(productId);
        List<Comment> comments = product.getComments();

        List<CommentResponse> response = new ArrayList<>();
        for(Comment comment : comments) {
            response.add(new CommentResponse(comment));
        }

        return response;
    }
}
