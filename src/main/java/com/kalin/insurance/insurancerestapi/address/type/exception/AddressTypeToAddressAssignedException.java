package com.kalin.insurance.insurancerestapi.address.type.exception;

/**
 * Exception thrown when there are addresses that have address type, specified by id, assigned.
 */
public class AddressTypeToAddressAssignedException extends RuntimeException {
    public AddressTypeToAddressAssignedException(Long id) {
        super("Address type with id: " + id + " is assigned to some address.");
    }
}
