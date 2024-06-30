package com.sparta.shoppingmall.domain.user.controller;

import com.sparta.shoppingmall.common.base.dto.CommonResponse;
import com.sparta.shoppingmall.common.security.UserDetailsImpl;
import com.sparta.shoppingmall.domain.user.dto.*;
import com.sparta.shoppingmall.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.sparta.shoppingmall.common.util.ControllerUtil.getResponseEntity;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    /**
     * 일반 회원 - 회원 가입
     */
    @PostMapping
    public ResponseEntity<CommonResponse> createUser(
            @Valid @RequestBody SignupRequest requestDTO
    ) {
        SignupResponse response = userService.createUser(requestDTO);
        return getResponseEntity(response, "회원가입 성공");
    }

    /**
     * 관리자 - 회원가입
     */
    @PostMapping("/admin")
    public ResponseEntity<CommonResponse> createAdminUser(
            @Valid @RequestBody SignupRequest requestDTO
    ) {
        SignupResponse response = userService.createAdminUser(requestDTO);
        return getResponseEntity(response, "회원가입 성공");
    }

    /**
     * 회원 탈퇴
     */
    @PatchMapping("/withdraw")
    public ResponseEntity<CommonResponse> withdrawUser(
            @Valid @RequestBody WithdrawRequest requestDTO,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        Long response = userService.withdrawUser(requestDTO, userDetails.getUser());
        return getResponseEntity(response, "회원탈퇴 성공");

    }

    /**
     * 로그아웃
     */
    @DeleteMapping("/logout")
    public ResponseEntity<CommonResponse> logout(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        Long response = userService.logout(userDetails.getUser().getId());
        return getResponseEntity(response, "로그아웃 성공");
    }

    /**
     * 로그인한 사용자 프로필 조회
     */
    @GetMapping("/{userId}/profiles")
    public ResponseEntity<CommonResponse> userProfile(
            @PathVariable Long userId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        ProfileResponse response = userService.inquiryUser(userId, userDetails.getUser());
        return getResponseEntity(response, "프로필 조회 성공");
    }

    /**
     * 로그인한 사용자 프로필 수정
     */
    @PatchMapping("/{userId}/profiles")
    public ResponseEntity<CommonResponse> updateProfile(
            @PathVariable Long userId,
            @Valid @RequestBody ProfileRequest request,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        UserResponse response = userService.editProfile(userId, request, userDetails.getUser());
        return getResponseEntity(response, "프로필 수정 성공");
    }

    /**
     * 비밀번호 변경
     */
    @PutMapping("/update-password")
    public ResponseEntity<CommonResponse> updatePassword(
            @Valid @RequestBody EditPasswordRequest request,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        UserResponse response = userService.editPassword(request, userDetails.getUser());
        return getResponseEntity(response, "비밀번호 변경 성공");
    }

    /**
     * 회원 전체조회 - 관리자
     */
    @Secured("ADMIN")
    @GetMapping("/admin")
    public ResponseEntity<CommonResponse> getUserList(){
        List<AdminUserResponse> response = userService.getUserList();
        return getResponseEntity(response, "회원 전체 조회 성공");
    }

    /**
     * 회원 정보 수정 - 관리자
     */
    @Secured("ADMIN")
    @PutMapping("/{userId}/admin")
    public ResponseEntity<CommonResponse> updateUser(
            @PathVariable Long userId,
            @Valid @RequestBody AdminUpdateUserRequest request
    ) {
        AdminUserResponse response = userService.updateUser(request, userId);
        return getResponseEntity(response, "회원 전체 조회 성공");
    }
}
