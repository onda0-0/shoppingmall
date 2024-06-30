package com.sparta.shoppingmall.domain.follows.controller;

import com.sparta.shoppingmall.common.base.dto.CommonResponse;
import com.sparta.shoppingmall.domain.follows.service.FollowsService;
import com.sparta.shoppingmall.domain.follows.dto.FollowsResponse;
import com.sparta.shoppingmall.common.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.sparta.shoppingmall.common.util.ControllerUtil.getBadRequestResponseEntity;
import static com.sparta.shoppingmall.common.util.ControllerUtil.getResponseEntity;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/follows")
public class FollowsController {

    private final FollowsService followsService;

    /**
     * 팔로우
     */
    @PostMapping("/{followingId}")
    public ResponseEntity<CommonResponse> followUser(
            @PathVariable Long followingId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        FollowsResponse response = followsService.followUser(followingId, userDetails.getUser());
        return getResponseEntity(response, "팔로우 성공");
    }

    /**
     * 팔로우 취소
     */
    @DeleteMapping("/{followingId}")
    public ResponseEntity<CommonResponse> followCancel(
            @PathVariable Long followingId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        FollowsResponse response = followsService.followCancel(followingId, userDetails.getUser());
        return getResponseEntity(response, "팔로우 취소 성공");
    }

    /**
     * 팔로잉 목록 조회
     */
    @GetMapping("/followings")
    public ResponseEntity<CommonResponse> getFollowings(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        List<FollowsResponse> response = followsService.getFollowings(userDetails.getUser());
        return getResponseEntity(response, "팔로잉 목록 조회 성공");
    }

    /**
     * 팔로워 목록 조회
     */
    @GetMapping("/followers")
    public ResponseEntity<CommonResponse> getFollowers(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        List<FollowsResponse> response = followsService.getFollowers(userDetails.getUser());
        return getResponseEntity(response, "팔로워 목록 조회 성공");
    }

    /**
     * 관리자 - 팔로우 취소
     */
    @Secured("ADMIN")
    @DeleteMapping("/{followerId}/{followingId}")
    public ResponseEntity<CommonResponse> followCancelAdmin(
            @PathVariable(name = "followerId") Long followerId,
            @PathVariable(name = "followingId") Long followingId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        FollowsResponse response = followsService.followCancelAdmin(followerId, followingId);
        return getResponseEntity(response, "해당 사용자의 팔로우 취소 성공");
    }

    /**
     * 관리자 - 해당 사용자의 팔로잉 목록 조회
     */
    @Secured("ADMIN")
    @GetMapping("/{followerId}/follower")
    public ResponseEntity<CommonResponse> getFollowingsAdmin(
            @PathVariable Long followerId
    ) {
        List<FollowsResponse> response = followsService.getFollowingsAdmin(followerId);
        return getResponseEntity(response, "해당 사용자의 팔로잉 목록 조회 성공");
    }

    /**
     * 관리자 - 해당 사용자의 팔로워 목록 조회
     */
    @Secured("ADMIN")
    @GetMapping("/{followingId}/following")
    public ResponseEntity<CommonResponse> getFollowersAdmin(
            @PathVariable Long followingId
    ) {
        List<FollowsResponse> response = followsService.getFollowersAdmin(followingId);
        return getResponseEntity(response, "해당 사용자의 팔로워 목록 조회 성공");
    }

}
