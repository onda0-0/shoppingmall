package com.sparta.shoppingmall.order.dto;

import com.sparta.shoppingmall.order.entity.Order;
import com.sparta.shoppingmall.order.status.OrderStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderResponseDto {
    private final Long orderid;
    private final Long userid;
    private final String address;
    private final int totalPrice;
    private final OrderStatus status;

    @Builder
    public OrderResponseDto(Order order) {
        this.orderid = order.getId();
        this.userid = order.getUserid();
        this.address = order.getAddress();
        this.totalPrice = order.getTotalPrice();
        this.status = order.getStatus();
    }
}
