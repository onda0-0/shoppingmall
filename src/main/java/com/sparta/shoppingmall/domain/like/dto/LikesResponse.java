package com.sparta.shoppingmall.domain.like.dto;

import com.sparta.shoppingmall.domain.like.entity.LikeStatus;
import com.sparta.shoppingmall.domain.like.entity.Likes;
import com.sparta.shoppingmall.domain.like.entity.ContentType;
import lombok.Getter;

@Getter
public class LikesResponse {

    private final Long id;
    private final ContentType contentType;
    private final Long contentId;
    private final LikeStatus status;

    public LikesResponse(Likes likes) {
        this.id = likes.getId();
        this.contentType = likes.getContentType();
        this.contentId = likes.getContentId();
        this.status = likes.getStatus();
    }

}
