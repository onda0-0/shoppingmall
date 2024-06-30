package com.sparta.shoppingmall.domain.comment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CommentRequest {

    @NotBlank(message = "내용을 입력해주세요.")
    private String content;

}
