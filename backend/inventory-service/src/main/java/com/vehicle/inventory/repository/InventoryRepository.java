package com.vehicle.inventory.repository;

import com.vehicle.inventory.model.InventoryItem;
import com.vehicle.inventory.model.InventoryStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryItem, Long> {
    Optional<InventoryItem> findByPartNumber(String partNumber);
    List<InventoryItem> findByStatus(InventoryStatus status);
    List<InventoryItem> findByCategory(String category);
    List<InventoryItem> findByQuantityInStockLessThanEqual(Integer quantity);
}
