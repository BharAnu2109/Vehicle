package com.vehicle.inventory.controller;

import com.vehicle.inventory.model.InventoryItem;
import com.vehicle.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class InventoryController {
    private final InventoryService inventoryService;

    @PostMapping
    public ResponseEntity<InventoryItem> createItem(@RequestBody InventoryItem item) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(inventoryService.createItem(item));
    }

    @GetMapping
    public ResponseEntity<List<InventoryItem>> getAllItems() {
        return ResponseEntity.ok(inventoryService.getAllItems());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryItem> getItemById(@PathVariable Long id) {
        return inventoryService.getItemById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/part/{partNumber}")
    public ResponseEntity<InventoryItem> getItemByPartNumber(@PathVariable String partNumber) {
        return inventoryService.getItemByPartNumber(partNumber)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/low-stock")
    public ResponseEntity<List<InventoryItem>> getLowStockItems() {
        return ResponseEntity.ok(inventoryService.getLowStockItems());
    }

    @GetMapping("/out-of-stock")
    public ResponseEntity<List<InventoryItem>> getOutOfStockItems() {
        return ResponseEntity.ok(inventoryService.getOutOfStockItems());
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventoryItem> updateItem(@PathVariable Long id,
                                                     @RequestBody InventoryItem item) {
        return ResponseEntity.ok(inventoryService.updateItem(id, item));
    }

    @PatchMapping("/{id}/adjust-stock")
    public ResponseEntity<InventoryItem> adjustStock(@PathVariable Long id,
                                                      @RequestParam Integer quantity) {
        return ResponseEntity.ok(inventoryService.adjustStock(id, quantity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        inventoryService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }
}
