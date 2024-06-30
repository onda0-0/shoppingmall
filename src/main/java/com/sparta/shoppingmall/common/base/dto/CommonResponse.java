package com.sparta.shoppingmall.common.base.dto;

import lombok.Builder;

@Builder
public class CommonResponse <T> {

    private int statusCode;
    private String message;
    private T data;

}
