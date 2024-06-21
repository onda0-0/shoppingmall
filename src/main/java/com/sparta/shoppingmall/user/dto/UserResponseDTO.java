package com.sparta.shoppingmall.user.dto;

import com.sparta.shoppingmall.user.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserResponseDTO {

    private Long id;

    private String username;

    private String email;

    private String address;

    private LocalDateTime createAt;

    private LocalDateTime uodateAt;

    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.address = user.getAddress();
        this.createAt = user.getCreateAt();
        this.uodateAt = user.getUpdateAt();
    }
}
