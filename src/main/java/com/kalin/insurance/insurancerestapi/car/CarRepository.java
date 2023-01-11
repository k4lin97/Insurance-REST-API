package com.kalin.insurance.insurancerestapi.car;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for the car.
 */
public interface CarRepository extends JpaRepository<Car, Long> {
}
