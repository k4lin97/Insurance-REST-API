package com.kalin.insurance.insurancerestapi.car.exception;

/**
 * Exception thrown when car is not found by ID.
 */
public class CarNotFoundException extends RuntimeException {
    public CarNotFoundException(Long id) {
        super("Car with id: " + id + " was not found");
    }
}
