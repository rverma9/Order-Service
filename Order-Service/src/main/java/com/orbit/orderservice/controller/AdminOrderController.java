package com.orbit.orderservice.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orbit.orderservice.dto.OrderResponseDto;
import com.orbit.orderservice.exception.OrderNotFoundException;
import com.orbit.orderservice.model.OrderStatus;
import com.orbit.orderservice.service.OrderService;

@RestController
@RequestMapping("/api/admin/orders")
public class AdminOrderController {

private final OrderService orderService;
	
	public AdminOrderController(OrderService orderService) {
		this.orderService = orderService;
	}
	
	@GetMapping
	public ResponseEntity<List<OrderResponseDto>> getAllOrders() {
		return ResponseEntity.ok(orderService.getAllOrders());
	}
	
	@GetMapping("/status/{status}")
	public ResponseEntity<List<OrderResponseDto>> getAdminOrderByStatus(@PathVariable OrderStatus status) {
		return ResponseEntity.ok(orderService.getAdminOrderByStatus(status));
	}
	
	@PatchMapping("/{id}/status")
	public ResponseEntity<OrderResponseDto> updateOrderStatus(@PathVariable Long id, @RequestParam OrderStatus status) throws OrderNotFoundException {
		return ResponseEntity.ok(orderService.updateOrderStatus(id,status));
	}
	
}
