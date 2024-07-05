package com.sparta.shoppingmall.domain.comment.dto;

import com.sparta.shoppingmall.domain.comment.entity.Comment;
import com.sparta.shoppingmall.domain.comment.service.CommentService;
import com.sparta.shoppingmall.domain.product.dto.ProductPageResponse;
import com.sparta.shoppingmall.domain.product.dto.ProductResponse;
import com.sparta.shoppingmall.domain.product.entity.Product;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Builder
public class CommentPageResponse {
    private final Integer currentPage;
    private final String totalComments;
    private final List<CommentResponse> commentList;

    public static CommentPageResponse of(Integer currentPage, String totalComments, Page<Comment> comments){
        return CommentPageResponse.builder()
                .currentPage(currentPage)
                .totalComments(totalComments)
                .commentList(comments.getContent().stream().map(CommentResponse::of).toList())
                .build();
    }
}
