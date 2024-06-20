package com.sparta.shoppingmall.cart.repository;

import com.sparta.shoppingmall.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {

}
