package com.sparta.shoppingmall.order.repository;

import com.sparta.shoppingmall.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserid(Long userId);
}
