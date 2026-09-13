package com.orbit.orderservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orbit.orderservice.model.Order;
import com.orbit.orderservice.model.OrderStatus;

public interface OrderRepository extends JpaRepository<Order, Long> {
	
	List<Order> findUserById(Long userId);
	
	List<Order> findByStatus(OrderStatus status);

}
