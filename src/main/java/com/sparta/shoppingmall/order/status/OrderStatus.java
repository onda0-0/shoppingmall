package com.sparta.shoppingmall.order.status;

public enum OrderStatus {
    PAYING("결재중"),
    APPROVED("결재됨"),
    CANCELED("취소됨");
    private final String description;

    OrderStatus(String description) {
        this.description = description;
    }
}
