package com.sparta.shoppingmall.order.controller;

import com.sparta.shoppingmall.base.dto.CommonResponse;
import com.sparta.shoppingmall.order.dto.OrderListResponseDto;
import com.sparta.shoppingmall.order.dto.OrderRequestDto;
import com.sparta.shoppingmall.order.dto.OrderResponseDto;
import com.sparta.shoppingmall.order.entity.Order;
import com.sparta.shoppingmall.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    //상품 주문
    @PostMapping
    public ResponseEntity<CommonResponse<OrderResponseDto>> createOrder(@RequestBody OrderRequestDto orderRequestDto/*,
                                                                        @AuthenticationPrincipal UserDetailsImpl userDetails*/) {
        OrderResponseDto orderResponse = orderService.createOrder(orderRequestDto/*, userDetails*/);
        CommonResponse<OrderResponseDto> response = CommonResponse.<OrderResponseDto>builder()
                .statusCode(200)
                .message("Order Success")
                .data(orderResponse)
                .build();
        return ResponseEntity.ok(response);
    }

    //주문내역 조회
    @GetMapping
    public ResponseEntity<CommonResponse<OrderListResponseDto>> getOrdersByUserId(/*@AuthenticationPrincipal UserDetailsImpl userDetails*/) {
        OrderListResponseDto orderlistResponse = orderService.getOrdersByUserId(/*userDetails*/);
        CommonResponse<OrderListResponseDto> response = CommonResponse.<OrderListResponseDto>builder()
                .statusCode(200)
                .message("Get Orders Success")
                .data(orderlistResponse)
                .build();
        return ResponseEntity.ok(response);
    }

    //주문 취소
    @DeleteMapping("/{orderid}")
    public ResponseEntity<CommonResponse<Long>> cancelOrder(@PathVariable Long orderid/*,
                                                            @AuthenticationPrincipal UserDetailsImpl userDetails*/) {
        boolean isCanceled = orderService.cancelOrder(orderid/*, userDetails*/);
        if (isCanceled) {
            CommonResponse<Long> response = CommonResponse.<Long>builder()
                    .statusCode(200)
                    .message("Delete Order Success")
                    .data(orderid)
                    .build();
            return ResponseEntity.ok(response);
        } else {
            CommonResponse<Long> response = CommonResponse.<Long>builder()
                    .statusCode(404)
                    .message("Order Not Found")
                    .data(orderid)
                    .build();
            return ResponseEntity.status(404).body(response);
        }
    }
}
