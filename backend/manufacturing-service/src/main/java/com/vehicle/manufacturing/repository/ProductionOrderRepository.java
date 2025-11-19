package com.vehicle.manufacturing.repository;

import com.vehicle.manufacturing.model.ProductionOrder;
import com.vehicle.manufacturing.model.ProductionStatus;
import com.vehicle.manufacturing.model.ProductionStage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductionOrderRepository extends JpaRepository<ProductionOrder, Long> {
    Optional<ProductionOrder> findByOrderNumber(String orderNumber);
    List<ProductionOrder> findByVehicleVin(String vehicleVin);
    List<ProductionOrder> findByStatus(ProductionStatus status);
    List<ProductionOrder> findByCurrentStage(ProductionStage stage);
    List<ProductionOrder> findByAssignedLine(String assignedLine);
}
