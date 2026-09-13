package com.orbit.orderservice.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.orbit.orderservice.client.ProductClient;
import com.orbit.orderservice.dto.OrderItemRequestDto;
import com.orbit.orderservice.dto.OrderItemResponseDto;
import com.orbit.orderservice.dto.OrderResponseDto;
import com.orbit.orderservice.dto.OrderRequestDto;
import com.orbit.orderservice.dto.ProductResponseDto;
import com.orbit.orderservice.exception.OrderNotFoundException;
import com.orbit.orderservice.model.Order;
import com.orbit.orderservice.model.OrderItem;
import com.orbit.orderservice.model.OrderStatus;
import com.orbit.orderservice.repository.OrderRepository;

import jakarta.transaction.Transactional;

@Service
public class OrderServiceImpl implements OrderService {

	private final ProductClient productClient;
	private final OrderRepository orderRepository;
	
	public OrderServiceImpl(ProductClient productClient, OrderRepository orderRepository) {
		super();
		this.productClient = productClient;
		this.orderRepository = orderRepository;
	}

	@Override
	public List<OrderResponseDto> getOrders() {
		return orderRepository.findAll().stream().map(this :: mapToDto).toList();
	}

	@Override
	public OrderResponseDto getOrderById(Long id) throws OrderNotFoundException {
		Order order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException("Order not found!"));
		return mapToDto(order);
	}

	@Override
	@Transactional
	public OrderResponseDto createOrder(String userId, OrderRequestDto request) {
		Order order = new Order();
		order.setOrderNumber(System.currentTimeMillis());
		order.setUserId(userId);
		order.setStatus(OrderStatus.CREATED);
		Double totalAmount = 0.0;
		List<OrderItem> orderItems = new ArrayList<>();
		for(OrderItemRequestDto itemRequest : request.getItems()) {
			ProductResponseDto product = productClient.getProductById(itemRequest.getProductId());
			if (product == null || !Boolean.TRUE.equals(product.getActive())) {
			    throw new RuntimeException("Product is not active or unavailable: " + itemRequest.getProductId());
			}
			
			if(!product.getActive()) {
				throw new RuntimeException("Product is inactive");
			}
			
			if(product.getStockQuantity() < itemRequest.getQuantity()) {
				throw new RuntimeException("Insufficient stock");
			}
			
			OrderItem item = new OrderItem();
			item.setProductId(product.getId());
			item.setQuantity(itemRequest.getQuantity());
			item.setPrice(product.getPrice());
			Double subtotal = product.getPrice() * itemRequest.getQuantity();
			item.setSubTotal(subtotal);
			item.setOrder(order);
			totalAmount += subtotal;
			orderItems.add(item);
		}
			order.setTotalAmount(totalAmount);
			order.setOrderItem(orderItems);
			Order savedOrder = orderRepository.save(order);
		return mapToDto(savedOrder);
	}

	@Override
	public OrderResponseDto cancelOrder(Long id) throws OrderNotFoundException {
		Order order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException("Order not found!"));
		order.setStatus(OrderStatus.CANCELLED);
		order.setUpdatedAt(LocalDateTime.now());
		Order savedOrder = orderRepository.save(order);
		return mapToDto(savedOrder);
	}

	@Override
	public List<OrderResponseDto> getAllOrders() {
		return orderRepository.findAll().stream().map(this :: mapToDto).toList();
	}

	@Override
	public List<OrderResponseDto> getAdminOrderByStatus(OrderStatus status) {
		return orderRepository.findByStatus(status).stream().map(this :: mapToDto).toList();
	}

	@Override
	public OrderResponseDto updateOrderStatus(Long id, OrderStatus status) throws OrderNotFoundException {
		Order order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException("Order not found!"));
		order.setStatus(status);
		Order savedOrder = orderRepository.save(order);
		return mapToDto(savedOrder);
	}

	private OrderResponseDto mapToDto(Order order) {

	    OrderResponseDto dto =  new OrderResponseDto();

	    dto.setId(order.getId());
	    dto.setOrderNumber( order.getOrderNumber());
	    dto.setTotalAmount(order.getTotalAmount());
	    dto.setStatus(order.getStatus());
	    dto.setCreatedAt(order.getCreatedAt());

	    List<OrderItemResponseDto> itemDtos = order.getOrderItem()
	                .stream()
	                .map(item -> {

	                    ProductResponseDto product =productClient.getProductById(item.getProductId());
	                    OrderItemResponseDto itemDto =new OrderItemResponseDto();

	                    itemDto.setProductId(item.getProductId());
	                    itemDto.setProductName(product.getName());
	                    itemDto.setQuantity(item.getQuantity());
	                    itemDto.setPrice( item.getPrice());
	                    itemDto.setSubtotal(item.getSubTotal());

	                    return itemDto;

	                }).toList();

	    dto.setItems(itemDtos);

	    return dto;
	}

}
