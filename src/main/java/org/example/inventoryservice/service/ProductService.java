package org.example.inventoryservice.service;

import org.example.inventoryservice.dto.ProductAvailability;

public interface ProductService {

    void updateProductAvailability(Long productId, ProductAvailability value);
}
