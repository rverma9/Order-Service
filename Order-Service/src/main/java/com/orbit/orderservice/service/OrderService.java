package com.orbit.orderservice.service;

import java.util.List;
import com.orbit.orderservice.dto.OrderResponseDto;
import com.orbit.orderservice.dto.OrderResuestDto;
import com.orbit.orderservice.exception.OrderNotFoundException;
import com.orbit.orderservice.model.OrderStatus;

public interface OrderService {
	
	List<OrderResponseDto> getOrders();
	
	OrderResponseDto getOrderById(Long id) throws OrderNotFoundException;
	
	OrderResponseDto createOrder(String userId, OrderResuestDto request);
	
	OrderResponseDto  cancelOrder(Long id) throws OrderNotFoundException;

	List<OrderResponseDto> getAllOrders();
	
	List<OrderResponseDto> getAdminOrderByStatus(OrderStatus status);
	
	OrderResponseDto updateOrderStatus(Long id, OrderStatus status) throws OrderNotFoundException;
}
