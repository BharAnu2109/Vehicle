package com.vehicle.service.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vehicle.service.model.Vehicle;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class VehicleEventProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    private static final String VEHICLE_TOPIC = "vehicle-events";

    public void sendVehicleCreatedEvent(Vehicle vehicle) {
        sendEvent("VEHICLE_CREATED", vehicle);
    }

    public void sendVehicleUpdatedEvent(Vehicle vehicle) {
        sendEvent("VEHICLE_UPDATED", vehicle);
    }

    public void sendVehicleDeletedEvent(Vehicle vehicle) {
        sendEvent("VEHICLE_DELETED", vehicle);
    }

    public void sendVehicleStatusChangedEvent(Vehicle vehicle) {
        sendEvent("VEHICLE_STATUS_CHANGED", vehicle);
    }

    private void sendEvent(String eventType, Vehicle vehicle) {
        try {
            String message = objectMapper.writeValueAsString(new VehicleEvent(eventType, vehicle));
            kafkaTemplate.send(VEHICLE_TOPIC, vehicle.getVin(), message);
            log.info("Sent {} event for vehicle VIN: {}", eventType, vehicle.getVin());
        } catch (JsonProcessingException e) {
            log.error("Error serializing vehicle event", e);
        }
    }

    private record VehicleEvent(String eventType, Vehicle vehicle) {}
}
