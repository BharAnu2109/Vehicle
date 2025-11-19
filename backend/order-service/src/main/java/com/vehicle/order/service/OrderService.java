package com.vehicle.order.service;

import com.vehicle.order.model.Order;
import com.vehicle.order.model.OrderStatus;
import com.vehicle.order.repository.OrderRepository;
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
public class OrderService {
    private final OrderRepository orderRepository;

    @Transactional
    public Order createOrder(Order order) {
        log.info("Creating order: {}", order.getOrderNumber());
        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public Optional<Order> getOrderByNumber(String orderNumber) {
        return orderRepository.findByOrderNumber(orderNumber);
    }

    public List<Order> getOrdersByCustomerId(String customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    public List<Order> getOrdersByStatus(OrderStatus status) {
        return orderRepository.findByStatus(status);
    }

    @Transactional
    public Order updateOrder(Long id, Order orderDetails) {
        log.info("Updating order with ID: {}", id);
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));

        order.setCustomerName(orderDetails.getCustomerName());
        order.setCustomerEmail(orderDetails.getCustomerEmail());
        order.setCustomerPhone(orderDetails.getCustomerPhone());
        order.setVehicleVin(orderDetails.getVehicleVin());
        order.setVehicleModel(orderDetails.getVehicleModel());
        order.setVehicleMake(orderDetails.getVehicleMake());
        order.setVehicleYear(orderDetails.getVehicleYear());
        order.setVehicleColor(orderDetails.getVehicleColor());
        order.setTotalPrice(orderDetails.getTotalPrice());
        order.setDepositAmount(orderDetails.getDepositAmount());
        order.setStatus(orderDetails.getStatus());
        order.setDeliveryAddress(orderDetails.getDeliveryAddress());
        order.setExpectedDeliveryDate(orderDetails.getExpectedDeliveryDate());
        order.setNotes(orderDetails.getNotes());

        return orderRepository.save(order);
    }

    @Transactional
    public Order updateOrderStatus(Long id, OrderStatus status) {
        log.info("Updating order status to {} for ID: {}", status, id);
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        
        order.setStatus(status);
        
        if (status == OrderStatus.DELIVERED) {
            order.setActualDeliveryDate(LocalDateTime.now());
        }

        return orderRepository.save(order);
    }

    @Transactional
    public void deleteOrder(Long id) {
        log.info("Deleting order with ID: {}", id);
        orderRepository.deleteById(id);
    }
}
