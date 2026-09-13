package com.orbit.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orbit.orderservice.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
