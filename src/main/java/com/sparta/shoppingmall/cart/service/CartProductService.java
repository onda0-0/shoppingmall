package com.sparta.shoppingmall.cart.service;

import com.sparta.shoppingmall.cart.dto.CartProductRequest;
import com.sparta.shoppingmall.cart.dto.CartProductResponse;
import com.sparta.shoppingmall.cart.entity.Cart;
import com.sparta.shoppingmall.cart.entity.CartProduct;
import com.sparta.shoppingmall.cart.repository.CartProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.sparta.shoppingmall.cart.entity.Cart.createCart;
import static com.sparta.shoppingmall.cart.entity.CartProduct.createCartProduct;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartProductService {

    private final CartProductRepository cartProductRepository;

    /**
     * 장바구니에 상품 담기
     * @param cartProductRequest
     * @return
     */
    public CartProductResponse addCartProduct(CartProductRequest cartProductRequest/*, Long userId*/) {

        //userId 체크
        //userId로 cart불러오기 로직 추가
        Cart cart = createCart();

        //상품 상태 체크 로직 추가
        //상품 상태가 ONSALE일 경우 장바구니에 추가 로직 진행
        //상품 상태가 ONSALE이 아닐 경우 throw IllegalArgumentException()
        CartProduct cartProduct = createCartProduct(cart/*, Product product*/);
        cartProductRepository.save(cartProduct);

        return CartProductResponse.builder()
                .id(cartProduct.getId())
                .cart(cart)
                //.product(product)
                .build();
    }
}
