package com.kalin.insurance.insurancerestapi.address.exception;

/**
 * Exception thrown when address is not found by ID.
 */
public class AddressNotFoundException extends RuntimeException {
    public AddressNotFoundException(Long id) {
        super("Address with id: " + id + " was not found");
    }
}
