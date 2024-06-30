package com.sparta.shoppingmall.domain.follows.entity;

import com.sparta.shoppingmall.common.base.entity.Timestamped;
import com.sparta.shoppingmall.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "follows")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Follows extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower_id")
    private User follower; //로그인한 유저

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "following_id")
    private User following; //로그인한 유저가 팔로우한 유저 (이쪽에 내가 많으면 좋은거)

    @Builder
    public Follows(User follower, User following) {
        this.follower = follower;
        this.following = following;
    }

    public static Follows createFollows(User follower, User following){
        return Follows.builder()
                .follower(follower)
                .following(following)
                .build();
    }

}
