package com.sparta.shoppingmall.domain.product.entity;

import lombok.Getter;

@Getter
public enum ProductStatus {
    RECOMMAND("Recommand"),
    ONSALE("Onsale"),//
    INPROGRESS("Inprogress"),
    COMPLETED("Completed");

    private final String status;

    ProductStatus(String status) {
        this.status = status;
    }
}
