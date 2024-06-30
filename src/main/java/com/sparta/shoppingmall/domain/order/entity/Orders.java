package com.sparta.shoppingmall.domain.order.entity;

import com.sparta.shoppingmall.common.base.entity.Timestamped;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Orders extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private Long productPrice;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_group_id")
    private OrderGroup orderGroup;

    @Builder
    public Orders(String productName, Long productPrice) {
        this.productName = productName;
        this.productPrice = productPrice;
    }

    public static Orders createOrders(String productName, Long productPrice){
        return Orders.builder()
                .productName(productName)
                .productPrice(productPrice)
                .build();
    }

}
