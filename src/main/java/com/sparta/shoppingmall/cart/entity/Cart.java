package com.sparta.shoppingmall.cart.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "cart")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CartProduct> cartProducts = new ArrayList<>();

    @Builder
    public Cart (Long id/*, User user*/, List<CartProduct> cartProducts) {
        this.id = id;
        this.cartProducts = cartProducts;
    }

    public void addCartProduct(CartProduct cartProduct) {
        this.cartProducts.add(cartProduct);
    }

    public static Cart createCart(/*User user*/) {
        Cart cart = new Cart();
        //cart.setUser(user);
        return cart;
    }

}
