package org.example.inventoryservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.inventoryservice.dto.InventoryDto;
import org.example.inventoryservice.dto.ProductAvailability;
import org.example.inventoryservice.entity.Inventory;
import org.example.inventoryservice.mapper.InventoryMapper;
import org.example.inventoryservice.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    private final InventoryMapper inventoryMapper;
    @Qualifier("openApiProductService")
    private final ProductService productService;

    public List<InventoryDto> getProductInventories(Long productId) {
        return inventoryRepository
                .findAllByProductId(productId)
                .stream()
                .map(inventoryMapper::mapToDto)
                .toList();
    }

    public void updateInventory(InventoryDto inventoryDto) {
        inventoryRepository
                .findByStoreId(inventoryDto.storeId())
                .ifPresentOrElse(
                                existingInventory ->{
                            log.info("Updating inventory for store: '{}'", inventoryDto.storeId());
                            existingInventory.setQuantity(inventoryDto.quantity());
                            inventoryRepository.save(existingInventory);

                                },
                                ()->{
                                    log.info("Creating new inventory for store: '{}'", inventoryDto.storeId());
                                        inventoryRepository.save(inventoryMapper.mapToInventory(inventoryDto));
                                });
        log.info("Checking other stores with product '{}'", inventoryDto.productId());
        int totalProductCount = inventoryRepository
                .findAllByProductId(inventoryDto.productId())
                .stream()
                .mapToInt(Inventory::getQuantity)
                .sum();
        log.info("The product '{}' has {} quantity", inventoryDto.productId(), totalProductCount);

        if (totalProductCount == 0) {
            log.info("Updating product status to OUT_OF_STOCK...");
            productService.updateProductAvailability(inventoryDto.productId(), ProductAvailability.OUT_OF_STOCK);
        } else {
            log.info("Updating product status to AVAILABLE...");
            productService.updateProductAvailability(inventoryDto.productId(), ProductAvailability.AVAILABLE);
        }
    }
}
