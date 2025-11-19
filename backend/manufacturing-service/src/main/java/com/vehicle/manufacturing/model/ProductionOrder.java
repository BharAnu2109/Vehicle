package com.vehicle.manufacturing.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "production_orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductionOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderNumber;
    private String vehicleVin;
    private String vehicleModel;
    private String vehicleMake;
    
    @Enumerated(EnumType.STRING)
    private ProductionStage currentStage;
    
    @Enumerated(EnumType.STRING)
    private ProductionStatus status;
    
    private Integer quantity;
    private LocalDateTime startDate;
    private LocalDateTime expectedCompletionDate;
    private LocalDateTime actualCompletionDate;
    private String assignedLine;
    private Double completionPercentage;
    private String notes;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (status == null) {
            status = ProductionStatus.PENDING;
        }
        if (currentStage == null) {
            currentStage = ProductionStage.PLANNING;
        }
        if (completionPercentage == null) {
            completionPercentage = 0.0;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
