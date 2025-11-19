package com.vehicle.service.repository;

import com.vehicle.service.model.Vehicle;
import com.vehicle.service.model.VehicleStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    Optional<Vehicle> findByVin(String vin);
    List<Vehicle> findByMake(String make);
    List<Vehicle> findByModel(String model);
    List<Vehicle> findByStatus(VehicleStatus status);
    List<Vehicle> findByYear(Integer year);
    List<Vehicle> findByMakeAndModel(String make, String model);
}
