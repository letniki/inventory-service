package org.example.inventoryservice.mapper;

import org.example.inventoryservice.dto.InventoryDto;
import org.example.inventoryservice.entity.Inventory;
import org.mapstruct.Mapper;

@Mapper
public interface InventoryMapper {
    InventoryDto mapToDto(Inventory inventory);
    Inventory mapToInventory(InventoryDto inventoryDto);
}
