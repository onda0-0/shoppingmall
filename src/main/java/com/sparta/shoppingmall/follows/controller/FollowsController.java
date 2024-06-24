package com.sparta.shoppingmall.follows.controller;

import com.sparta.shoppingmall.base.dto.CommonResponse;
import com.sparta.shoppingmall.follows.dto.FollowsResponse;
import com.sparta.shoppingmall.follows.service.FollowsService;
import com.sparta.shoppingmall.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.sparta.shoppingmall.util.ControllerUtil.getBadRequestResponseEntity;
import static com.sparta.shoppingmall.util.ControllerUtil.getResponseEntity;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/follows")
public class FollowsController {

    private final FollowsService followsService;

    /**
     * 사용자 follow
     */
    @PostMapping("/{followingId}")
    public ResponseEntity<CommonResponse> followUser(
            @PathVariable Long followingId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        try{
            FollowsResponse response = followsService.followUser(followingId, userDetails.getUser());
            return getResponseEntity(response, "팔로우 성공");
        } catch (Exception e) {
            return getBadRequestResponseEntity(e);
        }
    }

    /**
     * follow 취소
     */
    @DeleteMapping("/{followingId}")
    public ResponseEntity<CommonResponse> followCancel(
            @PathVariable Long followingId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        try{
            FollowsResponse response = followsService.followCancel(followingId, userDetails.getUser());
            return getResponseEntity(response, "팔로우 취소 성공");
        } catch(Exception e) {
            return getBadRequestResponseEntity(e);
        }

    }

}
