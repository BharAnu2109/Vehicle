package com.vehicle.manufacturing.controller;

import com.vehicle.manufacturing.model.ProductionOrder;
import com.vehicle.manufacturing.model.ProductionStage;
import com.vehicle.manufacturing.model.ProductionStatus;
import com.vehicle.manufacturing.service.ManufacturingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/manufacturing")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ManufacturingController {
    private final ManufacturingService manufacturingService;

    @PostMapping("/orders")
    public ResponseEntity<ProductionOrder> createOrder(@RequestBody ProductionOrder order) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(manufacturingService.createProductionOrder(order));
    }

    @GetMapping("/orders")
    public ResponseEntity<List<ProductionOrder>> getAllOrders() {
        return ResponseEntity.ok(manufacturingService.getAllOrders());
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<ProductionOrder> getOrderById(@PathVariable Long id) {
        return manufacturingService.getOrderById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/orders/number/{orderNumber}")
    public ResponseEntity<ProductionOrder> getOrderByNumber(@PathVariable String orderNumber) {
        return manufacturingService.getOrderByNumber(orderNumber)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/orders/status/{status}")
    public ResponseEntity<List<ProductionOrder>> getOrdersByStatus(@PathVariable ProductionStatus status) {
        return ResponseEntity.ok(manufacturingService.getOrdersByStatus(status));
    }

    @GetMapping("/orders/stage/{stage}")
    public ResponseEntity<List<ProductionOrder>> getOrdersByStage(@PathVariable ProductionStage stage) {
        return ResponseEntity.ok(manufacturingService.getOrdersByStage(stage));
    }

    @PutMapping("/orders/{id}")
    public ResponseEntity<ProductionOrder> updateOrder(@PathVariable Long id,
                                                       @RequestBody ProductionOrder order) {
        return ResponseEntity.ok(manufacturingService.updateOrder(id, order));
    }

    @PatchMapping("/orders/{id}/advance")
    public ResponseEntity<ProductionOrder> advanceStage(@PathVariable Long id,
                                                        @RequestParam ProductionStage stage) {
        return ResponseEntity.ok(manufacturingService.advanceStage(id, stage));
    }

    @DeleteMapping("/orders/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        manufacturingService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
