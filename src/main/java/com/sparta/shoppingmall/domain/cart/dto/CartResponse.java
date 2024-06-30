package com.sparta.shoppingmall.domain.cart.dto;

import com.sparta.shoppingmall.domain.cart.entity.CartProduct;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Builder
public class CartResponse {

    private final Integer currentPage;
    private final String totalCartProduct;
    private final List<CartProductResponse> cartProductList;

    public static CartResponse of(Integer currentPage, String totalCartProduct, Page<CartProduct> cartProductList) {
        return CartResponse.builder()
                .currentPage(currentPage)
                .totalCartProduct(totalCartProduct)
                .cartProductList(cartProductList.getContent().stream().map(CartProductResponse::of).toList())
                .build();
    }

}
