package com.sparta.shoppingmall.order.service;

import com.sparta.shoppingmall.order.dto.OrderListResponseDto;
import com.sparta.shoppingmall.order.dto.OrderRequestDto;
import com.sparta.shoppingmall.order.dto.OrderResponseDto;
import com.sparta.shoppingmall.order.entity.Order;
import com.sparta.shoppingmall.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    public OrderResponseDto createOrder(Long userId, OrderRequestDto orderRequestDto) {
        Order order = Order.builder()
                .userid(userId)
                .address(orderRequestDto.getAddress())
                .totalPrice(orderRequestDto.getTotalPrice())
                .build();
        order.approvedStatus();
        orderRepository.save(order);
        return new OrderResponseDto(order);
    }

    public OrderListResponseDto getOrdersByUserId(Long userid) {
        List<Order> orders = orderRepository.findByUserid(userid);
        return new OrderListResponseDto(orders);
    }

    @Transactional
    public boolean cancelOrder(Long orderid, Long userid) {
        Optional<Order> orderOptional = orderRepository.findById(orderid);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            if (order.getUserid().equals(userid)) {
                order.canceledStatus();
                orderRepository.save(order);
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
}
