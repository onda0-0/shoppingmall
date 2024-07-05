package com.sparta.shoppingmall.domain.user.dto;


import com.sparta.shoppingmall.domain.user.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ProfileResponse {

    private final Long id;

    private final String username;

    private final String email;

    private final String address;

    private final Long likedProductCount;
    private final Long likedCommentCount;

    private final LocalDateTime createAt;

    private final LocalDateTime updateAt;


    public ProfileResponse(User user,Long likedProductCount,Long likedCommentCount) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.address = user.getAddress();
        this.likedProductCount=likedProductCount;
        this.likedCommentCount=likedCommentCount;
        this.createAt = user.getCreateAt();
        this.updateAt = user.getUpdateAt();
    }
}
