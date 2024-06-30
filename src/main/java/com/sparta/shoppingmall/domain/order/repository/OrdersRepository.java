package com.sparta.shoppingmall.domain.order.repository;

import com.sparta.shoppingmall.domain.order.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders, Long> {



}
