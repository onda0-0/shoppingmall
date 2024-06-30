package com.sparta.shoppingmall.domain.like.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LikeStatus {

    LIKED("LIKED"),
    CANCELED("CANCELED");

    private final String likestatus;
}
