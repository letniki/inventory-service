package org.example.inventoryservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.inventoryservice.dto.InventoryDto;
import org.example.inventoryservice.service.InventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PutMapping("/inventories")
    public ResponseEntity<Void> updateInventory(@RequestBody InventoryDto inventoryDto){
        inventoryService.updateInventory(inventoryDto);
        return ResponseEntity.accepted().build();
    }
    @GetMapping("/inventories")
    public ResponseEntity<List<InventoryDto>> getAllInventory(@RequestParam Long productId){
        return ResponseEntity.ok(inventoryService.getProductInventories(productId));
    }
}
