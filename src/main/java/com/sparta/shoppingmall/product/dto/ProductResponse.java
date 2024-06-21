package com.sparta.shoppingmall.product.dto;

import com.sparta.shoppingmall.product.entity.Product;
import com.sparta.shoppingmall.product.entity.ProductStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductResponse {

    private final Long id;
    //private final User user;
    private final String name;
    private final ProductStatus status;

    @Builder
    public ProductResponse(Long id, /*User user,*/ String name, ProductStatus status) {
        this.id = id;
        //this.user=user;
        this.name = name;
        this.status = status;
    }
}
//응답에 주문정보는 필요없다고 생각해서 이렇게 일단 담았다.