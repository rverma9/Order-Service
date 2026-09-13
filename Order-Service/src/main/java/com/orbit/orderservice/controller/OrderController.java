package com.orbit.orderservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orbit.orderservice.dto.OrderRequestDto;
import com.orbit.orderservice.dto.OrderResponseDto;
import com.orbit.orderservice.exception.OrderNotFoundException;
import com.orbit.orderservice.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
	
	private final OrderService orderService;
	
	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}

	@GetMapping
	public ResponseEntity<List<OrderResponseDto>> getOrders(){
		return ResponseEntity.ok(orderService.getOrders());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable Long id) throws OrderNotFoundException {
		return ResponseEntity.ok(orderService.getOrderById(id));
	}
	
	@PostMapping
	public ResponseEntity<OrderResponseDto> createOrder(@RequestBody OrderRequestDto request) {
		String userId = "1L";
		return new ResponseEntity<>(orderService.createOrder(userId,request), HttpStatus.CREATED);
	}
	
	@PatchMapping("/{id}/cancel")
	public ResponseEntity<OrderResponseDto> cancelOrder(@PathVariable Long id) throws OrderNotFoundException {
		return ResponseEntity.ok(orderService.cancelOrder(id));
	}
}
