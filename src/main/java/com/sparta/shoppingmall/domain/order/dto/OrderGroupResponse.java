package com.sparta.shoppingmall.domain.order.dto;

import com.sparta.shoppingmall.domain.order.entity.OrderStatus;
import com.sparta.shoppingmall.domain.order.entity.OrderGroup;
import lombok.Getter;

import java.util.List;

@Getter
public class OrderGroupResponse {
    private final Long groupId;
    private final String address;
    private final Long totalPrice;
    private final OrderStatus status;
    private final List<OrdersResponse> OrdersResponses;

    public OrderGroupResponse(OrderGroup orderGroup, List<OrdersResponse> OrdersResponses) {
        this.groupId = orderGroup.getId();
        this.address = orderGroup.getAddress();
        this.totalPrice = orderGroup.getTotalPrice();
        this.status = orderGroup.getStatus();
        this.OrdersResponses = OrdersResponses;
    }
}
