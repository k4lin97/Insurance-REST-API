package com.kalin.insurance.insurancerestapi.client.exception;

/**
 * Exception thrown when client is not found by ID.
 */
public class ClientNotFoundException extends RuntimeException {
    public ClientNotFoundException(Long id) {
        super("Client with id: " + id + " was not found.");
    }
}
