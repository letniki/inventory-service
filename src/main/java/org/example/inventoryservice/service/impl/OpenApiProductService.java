package org.example.inventoryservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.inventoryservice.dto.ProductAvailability;
import org.example.inventoryservice.mapper.ProductMapper;
import org.example.inventoryservice.productapi.client.ProductApi;
import org.example.inventoryservice.productapi.client.ProductDto;
import org.example.inventoryservice.service.ProductService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@RequiredArgsConstructor
@Service
@Slf4j
public class OpenApiProductService implements ProductService{

    private final ProductApi productApi;

    private final ProductMapper productMapper;
    @Override
    public void updateProductAvailability(Long productId, ProductAvailability value) {
        ProductDto product = productApi.modifyProduct(productId, new ProductDto().availability(productMapper.mapAvailability(value)));
        log.info("Product availability updated: {}", product);
    }
}
