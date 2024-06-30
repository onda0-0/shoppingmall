package com.sparta.shoppingmall.domain.cart.controller;

import com.sparta.shoppingmall.common.base.dto.CommonResponse;
import com.sparta.shoppingmall.common.security.UserDetailsImpl;
import com.sparta.shoppingmall.domain.cart.dto.CartProductRequest;
import com.sparta.shoppingmall.domain.cart.dto.CartProductResponse;
import com.sparta.shoppingmall.domain.cart.dto.CartResponse;
import com.sparta.shoppingmall.domain.cart.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.sparta.shoppingmall.common.util.ControllerUtil.getResponseEntity;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/carts")
public class CartController {

    private final CartService cartService;

    /**
     * 장바구니에 상품 단건 담기
     */
    @PostMapping("/products")
    public ResponseEntity<CommonResponse> creatCartProduct(
            @Valid @RequestBody CartProductRequest cartProductRequest,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        CartProductResponse response = cartService.addCartProduct(cartProductRequest, userDetails.getUser());
        return getResponseEntity(response, "장바구니에 상품 추가 성공");
    }

    /**
     * 장바구니에 상품 리스트 조회
     */
    @GetMapping("/products")
    public ResponseEntity<CommonResponse> getCartProducts(
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") final Integer pageNum,
            @RequestParam(value = "isDesc", required = false, defaultValue = "true") final Boolean isDesc,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        CartResponse response = cartService.getCartProducts(pageNum, isDesc, userDetails.getUser());
        return getResponseEntity(response, "장바구니 조회 성공");
    }

    /**
     * 장바구니에 상품 단건 삭제
     */
    @DeleteMapping("/products/{productId}")
    public ResponseEntity<CommonResponse> deleteCartProduct(
            @PathVariable Long productId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        Long response = cartService.deleteCartProduct(productId, userDetails.getUser());
        return getResponseEntity(response, "장바구니에 상품 삭제 성공");
    }

}
