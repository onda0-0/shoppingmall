package com.sparta.shoppingmall.cart.controller;

import com.sparta.shoppingmall.base.dto.CommonResponse;
import com.sparta.shoppingmall.cart.dto.CartProductRequest;
import com.sparta.shoppingmall.cart.dto.CartProductResponse;
import com.sparta.shoppingmall.cart.service.CartProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.sparta.shoppingmall.util.ControllerUtil.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/carts")
public class CartProductController {

    private final CartProductService cartProductService;

    @PostMapping
    public ResponseEntity<CommonResponse<?>> creatCartProduct(
            @Valid @RequestBody CartProductRequest cartProductRequest,
            //@AuthenticationPrincipal UserDetailsImpl userDetails,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return getFieldErrorResponseEntity(bindingResult, "장바구니에 상품 추가 실패");
        }
        try{
            CartProductResponse response = cartProductService.addCartProduct(cartProductRequest/*, userDetails.getUser().getId()*/);
            return getResponseEntity(response, "장바구니에 상품 추가 성공");
        } catch (Exception e) {
            return getBadRequestResponseEntity(e);
        }
    }

}
