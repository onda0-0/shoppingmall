package com.sparta.shoppingmall.domain.order.controller;

import com.sparta.shoppingmall.common.base.dto.CommonResponse;
import com.sparta.shoppingmall.common.security.UserDetailsImpl;
import com.sparta.shoppingmall.domain.order.dto.OrderGroupRequest;
import com.sparta.shoppingmall.domain.order.dto.OrderGroupResponse;
import com.sparta.shoppingmall.domain.order.service.OrderGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.sparta.shoppingmall.common.util.ControllerUtil.getResponseEntity;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderGroupController {

    private final OrderGroupService orderGroupService;

    /**
     * 상품 주문
     */
    @PostMapping
    public ResponseEntity<CommonResponse<OrderGroupResponse>> createOrder(
            @RequestBody OrderGroupRequest orderGroupRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        OrderGroupResponse response = orderGroupService.createOrder(orderGroupRequestDto, userDetails.getUser());
        return getResponseEntity(response, "상품 주문 성공");
    }

    /**
     * 사용자의 모든 주문내역 조회하기
     */
    @GetMapping
    public ResponseEntity<CommonResponse<List<OrderGroupResponse>>> getOrderGroups(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        List<OrderGroupResponse> response = orderGroupService.getOrderGroups(userDetails.getUser());
        return getResponseEntity(response, "전체 주문내역 조회 성공");
    }

    /**
     * 사용자의 주문내역 상세 조회
     */
    @GetMapping("/{orderGroupId}")
    public ResponseEntity<CommonResponse<OrderGroupResponse>> getOrderGroup(
        @PathVariable Long orderGroupId,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        OrderGroupResponse response = orderGroupService.getOrderGroup(orderGroupId, userDetails.getUser());
        return getResponseEntity(response, "상세 주문내역 조회 성공");
    }

    /**
     * 주문 취소
     */
    @PutMapping("/{groupId}")
    public ResponseEntity<CommonResponse<Long>> cancelOrder(
            @PathVariable Long groupId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        Long response = orderGroupService.cancelOrder(groupId, userDetails.getUser());
        return getResponseEntity(response, "주문 취소 성공");
    }
}
