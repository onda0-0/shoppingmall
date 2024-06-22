package com.sparta.shoppingmall.user.entity;

import jakarta.persistence.Column;


public enum UserType {

    USER, // 일반 사용자
    ADMIN; // 관리자

    public String getAuthority() {
        switch (this) { // 사용자 분류
            case USER:
                return "ROLE_USER"; // 일반 사용자
            case ADMIN:
                return "ROLE_ADMIN"; // 관리자
            default:
                return null; // 해당 안될경우 null 처리
        }
    }
}
