package com.sparta.shoppingmall.domain.cart.dto;

import com.sparta.shoppingmall.domain.cart.entity.CartProduct;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CartProductResponse {

    private final Long cartId;
    private final String productName;
    private final Long productPrice;

    public static CartProductResponse of(CartProduct cartProduct) {
        return CartProductResponse.builder()
                .cartId(cartProduct.getCart().getId())
                .productName(cartProduct.getProduct().getName())
                .productPrice(cartProduct.getProduct().getPrice())
                .build();
    }

}
