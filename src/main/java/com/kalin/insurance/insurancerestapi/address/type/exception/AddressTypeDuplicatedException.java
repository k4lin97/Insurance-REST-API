package com.kalin.insurance.insurancerestapi.address.type.exception;

/**
 * Exception thrown when same address type is already in a database.
 */
public class AddressTypeDuplicatedException extends RuntimeException {
    public AddressTypeDuplicatedException(String message) {
        super("Address type: " + message + " already exist.");
    }
}
