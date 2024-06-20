package com.sparta.shoppingmall.order.dto;

import com.sparta.shoppingmall.order.entity.Order;
import com.sparta.shoppingmall.order.status.OrderStatus;

public class OrderResponseDto {
    private Long orderid;
    private Long userid;
    private String address;
    private int totalPrice;
    private OrderStatus status;

    public OrderResponseDto(Order order) {
        this.orderid = order.getId();
        this.userid = order.getUserid();
        this.address = order.getAddress();
        this.totalPrice = order.getTotalPrice();
        this.status = order.getStatus();
    }
}
