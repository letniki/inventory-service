package org.example.inventoryservice.dto;

import lombok.Builder;

@Builder
public record InventoryDto(Long storeId, Long productId, Integer quantity) {
}
