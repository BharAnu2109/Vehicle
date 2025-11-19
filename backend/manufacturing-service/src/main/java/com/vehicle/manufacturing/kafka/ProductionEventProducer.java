package com.vehicle.manufacturing.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vehicle.manufacturing.model.ProductionOrder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductionEventProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    private static final String PRODUCTION_TOPIC = "production-events";

    public void sendProductionOrderCreatedEvent(ProductionOrder order) {
        sendEvent("PRODUCTION_ORDER_CREATED", order);
    }

    public void sendProductionOrderUpdatedEvent(ProductionOrder order) {
        sendEvent("PRODUCTION_ORDER_UPDATED", order);
    }

    public void sendProductionStageChangedEvent(ProductionOrder order) {
        sendEvent("PRODUCTION_STAGE_CHANGED", order);
    }

    private void sendEvent(String eventType, ProductionOrder order) {
        try {
            String message = objectMapper.writeValueAsString(new ProductionEvent(eventType, order));
            kafkaTemplate.send(PRODUCTION_TOPIC, order.getOrderNumber(), message);
            log.info("Sent {} event for order: {}", eventType, order.getOrderNumber());
        } catch (JsonProcessingException e) {
            log.error("Error serializing production event", e);
        }
    }

    private record ProductionEvent(String eventType, ProductionOrder order) {}
}
