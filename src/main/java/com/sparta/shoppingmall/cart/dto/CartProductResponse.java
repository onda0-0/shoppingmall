package com.sparta.shoppingmall.cart.dto;

import com.sparta.shoppingmall.cart.entity.Cart;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CartProductResponse {

    private final Long id;
    private final Cart cart;
    //private final Product product;

    @Builder
    public CartProductResponse(Long id, Cart cart/*, Product product*/) {
        this.id = id;
        this.cart = cart;
        //this.prodcut = cartProduct.getProduct();
    }

}
