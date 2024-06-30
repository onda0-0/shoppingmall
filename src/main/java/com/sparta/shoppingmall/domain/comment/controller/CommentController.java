package com.sparta.shoppingmall.domain.comment.controller;

import com.sparta.shoppingmall.common.base.dto.CommonResponse;
import com.sparta.shoppingmall.common.security.UserDetailsImpl;
import com.sparta.shoppingmall.domain.comment.dto.CommentRequest;
import com.sparta.shoppingmall.domain.comment.dto.CommentResponse;
import com.sparta.shoppingmall.domain.comment.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.sparta.shoppingmall.common.util.ControllerUtil.getResponseEntity;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products/{productId}/comments")
public class CommentController {

    private final CommentService commentService;

    /**
     * 댓글 등록
     */
    @PostMapping
    public ResponseEntity<CommonResponse> createComment(
            @PathVariable Long productId,
            @Valid @RequestBody CommentRequest request,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        CommentResponse response = commentService.createComment(request, productId, userDetails.getUser());
        return getResponseEntity(response, "댓글 생성 성공");
    }

    /**
     * 해당 상품의 전체 댓글 조회
     */
    @GetMapping
    public ResponseEntity<CommonResponse> getComments(
            @PathVariable Long productId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        List<CommentResponse> response = commentService.getComments(productId);
        return getResponseEntity(response, "댓글 조회 성공");
    }

    /**
     * 댓글 수정
     */
    @PatchMapping("/{commentId}")
    public ResponseEntity<CommonResponse> updateComment(
            @PathVariable Long productId,
            @PathVariable Long commentId,
            @Valid @RequestBody CommentRequest request,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        CommentResponse response = commentService.updateComments(request, productId, commentId, userDetails.getUser());
        return getResponseEntity(response, "댓글 수정 성공");
    }

    /**
     * 댓글 삭제
     */
    @DeleteMapping("/{commentId}")
    public ResponseEntity<CommonResponse> deleteComment(
            @PathVariable Long productId,
            @PathVariable Long commentId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        Long response = commentService.deleteComment(productId, commentId, userDetails.getUser());
        return getResponseEntity(response, "댓글 삭제 성공");
    }

}
