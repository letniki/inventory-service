package org.example.inventoryservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.okten.springdemo.event.consumer.IProductDeletedConsumerService;
import org.okten.springdemo.event.dto.ProductDeletedPayload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductDeletedEventConsumerService implements IProductDeletedConsumerService {

    private final InventoryService inventoryService;

    @Override
    public void productDeleted(ProductDeletedPayload payload, ProductDeletedPayloadHeaders headers) {
        log.info("Received product deleted event: {}", payload);
        inventoryService.deleteInventoryOfProduct(Long.valueOf(payload.getProductId()));
    }
}
