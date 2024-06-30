package com.sparta.shoppingmall.domain.product.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductStatus {
    RECOMMEND("RECOMMEND"),
    ONSALE("ONSALE"),
    INPROGRESS("INPROGRESS"),
    COMPLETED("COMPLETED");

    private final String status;

}
