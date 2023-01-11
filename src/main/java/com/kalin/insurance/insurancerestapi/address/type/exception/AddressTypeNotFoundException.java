package com.kalin.insurance.insurancerestapi.address.type.exception;

/**
 * Exception thrown when address type is not found.
 */
public class AddressTypeNotFoundException extends RuntimeException {
    public AddressTypeNotFoundException(String message) {
        super("Address type: " + message + " was not found.");
    }

    public AddressTypeNotFoundException(Long id) {
        super("Address type with id: " + id + " was not found.");
    }
}
