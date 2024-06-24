package com.sparta.shoppingmall.follows.dto;

import com.sparta.shoppingmall.follows.entity.Follows;
import com.sparta.shoppingmall.user.entity.User;
import lombok.Getter;

@Getter
public class FollowsResponse {

    private final Long id;
    private final User follower;
    private final User following;

    public FollowsResponse(Follows follows) {
        this.id = follows.getId();
        this.follower = follows.getFollower();
        this.following = follows.getFollowing();
    }

}
