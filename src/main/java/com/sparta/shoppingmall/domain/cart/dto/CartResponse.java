package com.sparta.shoppingmall.domain.cart.dto;

import com.sparta.shoppingmall.domain.cart.entity.CartProduct;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
public class CartResponse {

    private final Long cartId;
    private final Page<CartProduct> cartProducts;

    @Builder
    public CartResponse(Long id, Page<CartProduct> cartProducts) {
        this.cartId = id;
        this.cartProducts = cartProducts;
    }

}
