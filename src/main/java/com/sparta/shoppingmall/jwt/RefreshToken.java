package com.sparta.shoppingmall.jwt;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String refreshToken;

    public RefreshToken(String username, String refreshToken) {
        this.username = username;
        this.refreshToken = refreshToken;
    }

    public void update(String newToken) {
        System.out.println("RefreshToken.update");
        this.refreshToken = newToken;
    }

    //로그인시 리프레시 토큰 초기화
    @Transactional
    public void refreshTokenReset(String refreshToken) {
        this.refreshToken = refreshToken;
    }


}