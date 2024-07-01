package com.sparta.shoppingmall.domain.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserType {

    USER("USER"), // 일반 사용자
    ADMIN("ADMIN"); // 관리자

    private final String userTypeName;

    public String getAuthority() {
        return switch (this) { // 사용자 분류
            case USER -> "USER"; // 일반 사용자
            case ADMIN -> "ADMIN"; // 관리자
        };
    }
}
