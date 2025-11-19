package com.vehicle.service.service;

import com.vehicle.service.kafka.VehicleEventProducer;
import com.vehicle.service.model.Vehicle;
import com.vehicle.service.model.VehicleStatus;
import com.vehicle.service.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class VehicleService {
    private final VehicleRepository vehicleRepository;
    private final VehicleEventProducer eventProducer;

    @Transactional
    public Vehicle createVehicle(Vehicle vehicle) {
        log.info("Creating vehicle with VIN: {}", vehicle.getVin());
        Vehicle savedVehicle = vehicleRepository.save(vehicle);
        eventProducer.sendVehicleCreatedEvent(savedVehicle);
        return savedVehicle;
    }

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    public Optional<Vehicle> getVehicleById(Long id) {
        return vehicleRepository.findById(id);
    }

    public Optional<Vehicle> getVehicleByVin(String vin) {
        return vehicleRepository.findByVin(vin);
    }

    public List<Vehicle> getVehiclesByMake(String make) {
        return vehicleRepository.findByMake(make);
    }

    public List<Vehicle> getVehiclesByModel(String model) {
        return vehicleRepository.findByModel(model);
    }

    public List<Vehicle> getVehiclesByStatus(VehicleStatus status) {
        return vehicleRepository.findByStatus(status);
    }

    @Transactional
    public Vehicle updateVehicle(Long id, Vehicle vehicleDetails) {
        log.info("Updating vehicle with ID: {}", id);
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found with id: " + id));

        vehicle.setModel(vehicleDetails.getModel());
        vehicle.setMake(vehicleDetails.getMake());
        vehicle.setYear(vehicleDetails.getYear());
        vehicle.setColor(vehicleDetails.getColor());
        vehicle.setType(vehicleDetails.getType());
        vehicle.setEngineType(vehicleDetails.getEngineType());
        vehicle.setTransmission(vehicleDetails.getTransmission());
        vehicle.setPrice(vehicleDetails.getPrice());
        vehicle.setStatus(vehicleDetails.getStatus());
        vehicle.setManufacturingDate(vehicleDetails.getManufacturingDate());

        Vehicle updatedVehicle = vehicleRepository.save(vehicle);
        eventProducer.sendVehicleUpdatedEvent(updatedVehicle);
        return updatedVehicle;
    }

    @Transactional
    public void deleteVehicle(Long id) {
        log.info("Deleting vehicle with ID: {}", id);
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found with id: " + id));
        vehicleRepository.delete(vehicle);
        eventProducer.sendVehicleDeletedEvent(vehicle);
    }

    @Transactional
    public Vehicle updateVehicleStatus(Long id, VehicleStatus status) {
        log.info("Updating vehicle status to {} for ID: {}", status, id);
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found with id: " + id));
        vehicle.setStatus(status);
        Vehicle updatedVehicle = vehicleRepository.save(vehicle);
        eventProducer.sendVehicleStatusChangedEvent(updatedVehicle);
        return updatedVehicle;
    }
}
