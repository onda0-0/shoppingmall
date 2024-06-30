package com.sparta.shoppingmall.domain.like.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ContentType {

    PRODUCT("PRODUCT"),
    COMMENT("COMMENT");

    private final String contentType;

}
