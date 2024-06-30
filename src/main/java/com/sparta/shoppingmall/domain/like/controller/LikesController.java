package com.sparta.shoppingmall.domain.like.controller;

import com.sparta.shoppingmall.common.base.dto.CommonResponse;
import com.sparta.shoppingmall.common.exception.customexception.LikeMismatchException;
import com.sparta.shoppingmall.domain.like.dto.LikesResponse;
import com.sparta.shoppingmall.domain.like.service.LikesService;
import com.sparta.shoppingmall.domain.like.dto.LikesRequest;
import com.sparta.shoppingmall.common.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

import static com.sparta.shoppingmall.common.util.ControllerUtil.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LikesController {

    private final LikesService likesService;

    /**
     * 상품 좋아요 토글
     */
    @PostMapping("/products/{productId}/like")
    public ResponseEntity<CommonResponse> toggleProductLike (
            @PathVariable Long productId,
            @Valid @RequestBody LikesRequest request,
            BindingResult bindingResult,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
       if(bindingResult.hasErrors()) {
           return getFieldErrorResponseEntity(bindingResult, "상품 좋아요 토글 실패");
       }
       verifyPathVariable(productId, request.getContentId());

       LikesResponse response = likesService.toggleLike(request, userDetails.getUser());
       return getResponseEntity(response, "상품 좋아요 토글 성공");
    }

    /**
     * 댓글 좋아요 토글
     */
    @PostMapping("/comments/{commentId}/like")
    public ResponseEntity<CommonResponse> toggleCommentLike (
            @PathVariable Long commentId,
            @Valid @RequestBody LikesRequest request,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            BindingResult bindingResult
    ) {
        if(bindingResult.hasErrors()) {
            return getFieldErrorResponseEntity(bindingResult, "댓글 좋아요 토글 실패");
        }
        verifyPathVariable(commentId, request.getContentId());

        LikesResponse response = likesService.toggleLike(request, userDetails.getUser());
        return getResponseEntity(response, "댓글 좋아요 토글 성공");
    }

    private void verifyPathVariable(Long contentId, Long requestId) {
        if (!Objects.equals(contentId, requestId)) {
            throw new LikeMismatchException("PathVariable의 contentId와 RequestBody의 contentId가 다릅니다.");
        }
    }

}
