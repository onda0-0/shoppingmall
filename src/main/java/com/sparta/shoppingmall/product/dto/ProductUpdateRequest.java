package com.sparta.shoppingmall.product.dto;

import com.sparta.shoppingmall.product.entity.ProductStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ProductUpdateRequest {
    @NotBlank(message = "상품 이름은 필수 값 입니다.")
    private String name;

    @NotNull(message = "상품 가격은 필수 값 입니다.")
    private double price;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;
}
