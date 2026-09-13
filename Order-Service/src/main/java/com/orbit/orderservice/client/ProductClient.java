package com.orbit.orderservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.orbit.orderservice.dto.ProductResponseDto;

@FeignClient(name="product-service")
public interface ProductClient {

	@GetMapping("/api/products/{id}")
	ProductResponseDto getProductById(@PathVariable Long id);
}
