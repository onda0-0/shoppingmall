package com.sparta.shoppingmall.user.dto;


import com.sparta.shoppingmall.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class EditProfileResponseDTO {

    private Long id;

    private String username;

    private String email;

    private String address;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;


    public EditProfileResponseDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.address = user.getAddress();
        this.createAt = user.getCreateAt();
        this.updateAt = user.getUpdateAt();
    }
}
