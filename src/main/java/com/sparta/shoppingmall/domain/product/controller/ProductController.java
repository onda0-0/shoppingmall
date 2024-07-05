package com.sparta.shoppingmall.domain.product.controller;

import com.sparta.shoppingmall.common.base.dto.CommonResponse;
import com.sparta.shoppingmall.common.security.UserDetailsImpl;
import com.sparta.shoppingmall.domain.like.service.LikesService;
import com.sparta.shoppingmall.domain.product.dto.ProductRequest;
import com.sparta.shoppingmall.domain.product.dto.ProductResponse;
import com.sparta.shoppingmall.domain.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.sparta.shoppingmall.common.util.ControllerUtil.getResponseEntity;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final LikesService likesService;

    /**
     * 상품등록
     */
    @PostMapping
    public ResponseEntity<CommonResponse<ProductResponse>> createProduct(
            @Valid @RequestBody ProductRequest productRequest,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        ProductResponse response = productService.createProduct(productRequest, userDetails.getUser().getId());
        return getResponseEntity(response, "상품 등록 성공");
    }

    /**
     * 상품조회(전체) get api/products
     */
    @GetMapping
    public ResponseEntity<CommonResponse<List<ProductResponse>>> getProducts(
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") final Integer pageNum,
            @RequestParam(value = "isDesc", required = false, defaultValue = "true") final Boolean isDesc
    ) {
        List<ProductResponse> response = productService.getProducts(pageNum, isDesc);
        return getResponseEntity(response, "상품 목록 조회 성공");
    }


    /**
     * 상품조회(단일)get api/products/{productId}
     */
    @GetMapping("/{productId}")
    public ResponseEntity<CommonResponse<ProductResponse>> getProduct(
            @PathVariable Long productId
    ){
        ProductResponse response = productService.getProduct(productId);
        return getResponseEntity(response, "상품 조회 성공");
    }


    /**
     * 상품수정 api/products/{productId}
     */
    @PatchMapping("/{productId}")
    public ResponseEntity<CommonResponse<ProductResponse>> updateProduct(
            @PathVariable Long productId,
            @Valid @RequestBody ProductRequest productRequest,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        ProductResponse response = productService.updateProduct(productId, productRequest, userDetails.getUser());
        return getResponseEntity(response, "상품 수정 성공");
    }

    /**
     * 상품삭제 delete  api/products
     */
    @DeleteMapping("/{productId}")
    public ResponseEntity<CommonResponse<Long>> deleteProduct(
            @PathVariable Long productId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        Long response = productService.deleteProduct(productId, userDetails.getUser());
        return getResponseEntity(response, "상품 삭제 성공");
    }

    /**
     * 내가 좋아하는 게시글 목록 조회
     */
    @GetMapping("/liked")
    public ResponseEntity<CommonResponse<List<ProductResponse>>> getLikedProductsByUser(
            @RequestParam Long userId,
            @RequestParam int page,
            @RequestParam(defaultValue = "true") boolean isDesc
    ){
        List<ProductResponse> response = likesService.getLikedProductsByUser(userId, page, isDesc);
        return getResponseEntity(response, "내가 좋아하는 게시글 목록조회 기능 추가");
    }
    /*@GetMapping("/liked")
    public ResponseEntity<CommonResponse<List<ProductResponse>>> getLikedPostByUser(
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") final Integer pageNum,
            @RequestParam(value = "isDesc", required = false, defaultValue = "true") final Boolean isDesc
    ){
        List<ProductResponse> response = productService.getLikedProducts(pageNum, isDesc);
        return getResponseEntity(response, "내가 좋아하는 게시글 목록조회 기능 추가");
    }*/
    /*@GetMapping("/liked")
    public ResponseEntity<CommonResponse<List<ProductResponse>>> getLikedProductsByUser(
            @RequestParam Long userId,
            @RequestParam int page,
            @RequestParam(defaultValue = "true") boolean isDesc
    ){
        List<ProductResponse> response = likesService.getLikedProductsByUser(userId, page, isDesc);
        return getResponseEntity(response, "내가 좋아하는 게시글 목록조회 기능 추가");
    }*/

}

