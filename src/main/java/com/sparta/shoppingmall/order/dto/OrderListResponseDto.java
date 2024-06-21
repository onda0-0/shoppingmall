package com.sparta.shoppingmall.order.dto;

import com.sparta.shoppingmall.order.entity.Order;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class OrderListResponseDto {
    private final List<Order> orders;

    @Builder
    public OrderListResponseDto(List<Order> orders) {
        this.orders = orders;
    }
}
