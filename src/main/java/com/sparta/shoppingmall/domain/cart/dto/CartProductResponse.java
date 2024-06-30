package com.sparta.shoppingmall.domain.cart.dto;

import com.sparta.shoppingmall.domain.cart.entity.Cart;
import com.sparta.shoppingmall.domain.product.entity.Product;
import lombok.Getter;

@Getter
public class CartProductResponse {

    private final Long id;
    private final Cart cart;
    private final Product product;

    public CartProductResponse(Long id, Cart cart, Product product) {
        this.id = id;
        this.cart = cart;
        this.product = product;
    }

}
