package com.sparta.shoppingmall.product.entity;

import com.sparta.shoppingmall.base.entity.Timestamped;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name="product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @Column
    private String name;  //상품명

    private double price; //가격

    @Enumerated(EnumType.STRING)
    private ProductStatus status; //상태

    // 좋아요 수

    @Builder
    public Product(String name, User user, double price, ProductStatus status){
        this.user = user;
        this.name = name;
        this.price = price;
        this.status = status;
    }

    public void update(String name, double price, ProductStatus status) {
        this.name = name;
        this.price = price;
        this.status = status;
    }


    /* => 오더따라 바뀜. -> 즉 상품인 내가 해결할수 없음. - 오더꺼ㅓㅓㅓㅓ
    public void removeFromOrder() {
        if (this.order != null) {
            this.order.removeProduct(this); // 상품이 속한 주문에서 이 상품을 제거
            this.order = null; // 주문과의 연관 관계를 해제
            this.status = ProductStatus.ONSALE.; // 다시 판매 가능한 상태로 변경
        }
    }
    */


}