package com.sparta.shoppingmall.domain.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserStatus {

    JOIN("JOIN"), // 회원가입 상태
    WITHDRAW("WITHDRAW"), // 회원탈퇴 상태
    BLOCK("BLOCK"); // 회원차단 상태

    private final String userStatus;

}
