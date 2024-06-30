package com.sparta.shoppingmall.domain.follows.dto;

import com.sparta.shoppingmall.domain.follows.entity.Follows;
import lombok.Getter;

@Getter
public class FollowsResponse {

    private final Long id;
    private final String followerUsername;
    private final String followerName;
    private final String followingUsername;
    private final String followingName;

    public FollowsResponse(Follows follows) {
        this.id = follows.getId();
        this.followerUsername = follows.getFollower().getUsername();
        this.followerName = follows.getFollower().getName();
        this.followingUsername = follows.getFollowing().getUsername();
        this.followingName = follows.getFollowing().getName();
    }

}
