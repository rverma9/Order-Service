package com.orbit.orderservice.dto;

import java.util.List;

public class OrderResuestDto {

	private List<OrderItemRequestDto> items;

	public List<OrderItemRequestDto> getItems() {
		return items;
	}

	public void setItems(List<OrderItemRequestDto> items) {
		this.items = items;
	}
	
}
