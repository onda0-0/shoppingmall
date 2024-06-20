package com.sparta.shoppingmall.order.dto;

import com.sparta.shoppingmall.order.entity.Order;

import java.util.List;

public class OrderListResponseDto {
    private List<Order> orders;

    public OrderListResponseDto(List<Order> orders) {
        this.orders = orders;
    }
}
