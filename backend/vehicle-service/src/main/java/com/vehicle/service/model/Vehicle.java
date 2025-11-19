package com.vehicle.service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "vehicles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "VIN is required")
    @Column(unique = true)
    private String vin;

    @NotBlank(message = "Model is required")
    private String model;

    @NotBlank(message = "Make is required")
    private String make;

    @NotNull(message = "Year is required")
    private Integer year;

    @NotBlank(message = "Color is required")
    private String color;

    @NotBlank(message = "Type is required")
    private String type;

    private String engineType;
    private String transmission;
    private Double price;
    
    @Enumerated(EnumType.STRING)
    private VehicleStatus status;

    private LocalDateTime manufacturingDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (status == null) {
            status = VehicleStatus.IN_PRODUCTION;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
