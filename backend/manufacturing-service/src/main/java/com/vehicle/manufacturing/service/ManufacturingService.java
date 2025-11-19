package com.vehicle.manufacturing.service;

import com.vehicle.manufacturing.kafka.ProductionEventProducer;
import com.vehicle.manufacturing.model.ProductionOrder;
import com.vehicle.manufacturing.model.ProductionStage;
import com.vehicle.manufacturing.model.ProductionStatus;
import com.vehicle.manufacturing.repository.ProductionOrderRepository;
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
public class ManufacturingService {
    private final ProductionOrderRepository orderRepository;
    private final ProductionEventProducer eventProducer;

    @Transactional
    public ProductionOrder createProductionOrder(ProductionOrder order) {
        log.info("Creating production order: {}", order.getOrderNumber());
        ProductionOrder savedOrder = orderRepository.save(order);
        eventProducer.sendProductionOrderCreatedEvent(savedOrder);
        return savedOrder;
    }

    public List<ProductionOrder> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<ProductionOrder> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public Optional<ProductionOrder> getOrderByNumber(String orderNumber) {
        return orderRepository.findByOrderNumber(orderNumber);
    }

    public List<ProductionOrder> getOrdersByStatus(ProductionStatus status) {
        return orderRepository.findByStatus(status);
    }

    public List<ProductionOrder> getOrdersByStage(ProductionStage stage) {
        return orderRepository.findByCurrentStage(stage);
    }

    @Transactional
    public ProductionOrder updateOrder(Long id, ProductionOrder orderDetails) {
        log.info("Updating production order with ID: {}", id);
        ProductionOrder order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Production order not found with id: " + id));

        order.setVehicleVin(orderDetails.getVehicleVin());
        order.setVehicleModel(orderDetails.getVehicleModel());
        order.setVehicleMake(orderDetails.getVehicleMake());
        order.setCurrentStage(orderDetails.getCurrentStage());
        order.setStatus(orderDetails.getStatus());
        order.setQuantity(orderDetails.getQuantity());
        order.setExpectedCompletionDate(orderDetails.getExpectedCompletionDate());
        order.setActualCompletionDate(orderDetails.getActualCompletionDate());
        order.setAssignedLine(orderDetails.getAssignedLine());
        order.setCompletionPercentage(orderDetails.getCompletionPercentage());
        order.setNotes(orderDetails.getNotes());

        ProductionOrder updatedOrder = orderRepository.save(order);
        eventProducer.sendProductionOrderUpdatedEvent(updatedOrder);
        return updatedOrder;
    }

    @Transactional
    public ProductionOrder advanceStage(Long id, ProductionStage newStage) {
        log.info("Advancing production order {} to stage: {}", id, newStage);
        ProductionOrder order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Production order not found with id: " + id));
        
        order.setCurrentStage(newStage);
        order.setStatus(ProductionStatus.IN_PROGRESS);
        
        // Calculate completion percentage based on stage
        order.setCompletionPercentage(calculateCompletionPercentage(newStage));
        
        if (newStage == ProductionStage.COMPLETED) {
            order.setStatus(ProductionStatus.COMPLETED);
            order.setActualCompletionDate(LocalDateTime.now());
        }

        ProductionOrder updatedOrder = orderRepository.save(order);
        eventProducer.sendProductionStageChangedEvent(updatedOrder);
        return updatedOrder;
    }

    @Transactional
    public void deleteOrder(Long id) {
        log.info("Deleting production order with ID: {}", id);
        ProductionOrder order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Production order not found with id: " + id));
        orderRepository.delete(order);
    }

    private Double calculateCompletionPercentage(ProductionStage stage) {
        return switch (stage) {
            case PLANNING -> 5.0;
            case MATERIAL_PROCUREMENT -> 15.0;
            case FRAME_ASSEMBLY -> 30.0;
            case BODY_ASSEMBLY -> 45.0;
            case PAINTING -> 60.0;
            case ENGINE_INSTALLATION -> 75.0;
            case INTERIOR_ASSEMBLY -> 85.0;
            case QUALITY_INSPECTION -> 95.0;
            case FINAL_TESTING -> 98.0;
            case COMPLETED -> 100.0;
        };
    }
}
