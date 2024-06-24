package com.sparta.shoppingmall.follows.service;

import com.sparta.shoppingmall.follows.dto.FollowsResponse;
import com.sparta.shoppingmall.follows.entity.Follows;
import com.sparta.shoppingmall.follows.repository.FollowsRepository;
import com.sparta.shoppingmall.user.entity.User;
import com.sparta.shoppingmall.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FollowsService {

    private final UserService userService;
    private final FollowsRepository followsRepository;

    /**
     * 팔로우
     */
    public FollowsResponse followUser(Long followingId, User follower) {
        if(followingId.equals(follower.getId())){
            throw new IllegalArgumentException("자신을 팔로우 할 수 없습니다.");
        }

        Optional<Follows> checkFollow = followsRepository.findByFollowingIdAndFollowerId(followingId, follower.getId());
        if(checkFollow.isPresent()){
            throw new IllegalArgumentException("이미 팔로우 한 사용자 입니다.");
        }

        User following = userService.findById(followingId);
        Follows follow = new Follows(follower, following);
        followsRepository.save(follow);

        return new FollowsResponse(follow);
    }

}
