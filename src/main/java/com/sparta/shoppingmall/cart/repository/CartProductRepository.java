package com.sparta.shoppingmall.cart.repository;

import com.sparta.shoppingmall.cart.entity.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartProductRepository extends JpaRepository<CartProduct, Long> {

    //Page<CartProduct> findAllByCartId(Long cartId, Pageable pageable);

}
