package com.vehicle.inventory.service;

import com.vehicle.inventory.model.InventoryItem;
import com.vehicle.inventory.model.InventoryStatus;
import com.vehicle.inventory.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    @Transactional
    public InventoryItem createItem(InventoryItem item) {
        log.info("Creating inventory item: {}", item.getPartNumber());
        return inventoryRepository.save(item);
    }

    public List<InventoryItem> getAllItems() {
        return inventoryRepository.findAll();
    }

    public Optional<InventoryItem> getItemById(Long id) {
        return inventoryRepository.findById(id);
    }

    public Optional<InventoryItem> getItemByPartNumber(String partNumber) {
        return inventoryRepository.findByPartNumber(partNumber);
    }

    public List<InventoryItem> getLowStockItems() {
        return inventoryRepository.findByStatus(InventoryStatus.LOW_STOCK);
    }

    public List<InventoryItem> getOutOfStockItems() {
        return inventoryRepository.findByStatus(InventoryStatus.OUT_OF_STOCK);
    }

    @Transactional
    public InventoryItem updateItem(Long id, InventoryItem itemDetails) {
        log.info("Updating inventory item with ID: {}", id);
        InventoryItem item = inventoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory item not found with id: " + id));

        item.setPartName(itemDetails.getPartName());
        item.setCategory(itemDetails.getCategory());
        item.setDescription(itemDetails.getDescription());
        item.setQuantityInStock(itemDetails.getQuantityInStock());
        item.setReorderLevel(itemDetails.getReorderLevel());
        item.setMaxStockLevel(itemDetails.getMaxStockLevel());
        item.setSupplier(itemDetails.getSupplier());
        item.setUnitPrice(itemDetails.getUnitPrice());
        item.setLocation(itemDetails.getLocation());

        return inventoryRepository.save(item);
    }

    @Transactional
    public InventoryItem adjustStock(Long id, Integer quantity) {
        log.info("Adjusting stock for item ID: {} by {}", id, quantity);
        InventoryItem item = inventoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory item not found with id: " + id));

        item.setQuantityInStock(item.getQuantityInStock() + quantity);
        if (quantity > 0) {
            item.setLastRestocked(LocalDateTime.now());
        }

        return inventoryRepository.save(item);
    }

    @Transactional
    public void deleteItem(Long id) {
        log.info("Deleting inventory item with ID: {}", id);
        inventoryRepository.deleteById(id);
    }
}
