package com.orbit.orderservice.client;

import org.springframework.stereotype.Component;
import com.orbit.orderservice.dto.ProductResponseDto;

@Component
public class ProductClientFallback implements ProductClient {

    @Override
    public ProductResponseDto getProductById(Long id) {
        // Safe default response when product-service is down or failing
        ProductResponseDto fallback = new ProductResponseDto();
        fallback.setId(id);
        fallback.setName("Product Service Unavailable");
        fallback.setPrice(0.0);
        fallback.setStockQuantity(0L);
        fallback.setActive(false);
        return fallback;
    }
}