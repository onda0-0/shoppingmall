package com.sparta.shoppingmall.domain.follows.dto;

import com.sparta.shoppingmall.domain.follows.entity.Follows;
import lombok.Getter;

@Getter
public class FollowsResponse {

    private final Long id;
    private final Long followerId;
    private final Long followingId;

    public FollowsResponse(Follows follows) {
        this.id = follows.getId();
        this.followerId = follows.getFollower().getId();
        this.followingId = follows.getFollowing().getId();
    }

}
