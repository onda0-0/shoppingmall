package com.sparta.shoppingmall.domain.like.dto;

import com.sparta.shoppingmall.domain.like.entity.ContentType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LikesRequest {

    @NotNull(message = "컨텐츠 유형을 입력하세요")
    private ContentType contentType;

    @NotNull(message = "컨텐츠 id 값이 없습니다.")
    private Long contentId;

}
