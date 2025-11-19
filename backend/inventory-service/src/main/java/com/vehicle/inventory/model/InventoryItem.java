package com.vehicle.inventory.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "inventory_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String partNumber;
    private String partName;
    private String category;
    private String description;
    private Integer quantityInStock;
    private Integer reorderLevel;
    private Integer maxStockLevel;
    private String supplier;
    private Double unitPrice;
    private String location;
    
    @Enumerated(EnumType.STRING)
    private InventoryStatus status;
    
    private LocalDateTime lastRestocked;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (status == null) {
            status = InventoryStatus.IN_STOCK;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
        updateStatus();
    }

    private void updateStatus() {
        if (quantityInStock == 0) {
            status = InventoryStatus.OUT_OF_STOCK;
        } else if (quantityInStock <= reorderLevel) {
            status = InventoryStatus.LOW_STOCK;
        } else {
            status = InventoryStatus.IN_STOCK;
        }
    }
}
