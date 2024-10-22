package org.example.inventoryservice.repository;

import org.bson.types.ObjectId;
import org.example.inventoryservice.entity.Inventory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends MongoRepository<Inventory, ObjectId> {

    Optional<Inventory> findByStoreIdAndProductId(Long storeId, Long productId);
    List<Inventory> findAllByProductId(Long productId);

    void deleteInventoryByProductId(Long productId);
}
