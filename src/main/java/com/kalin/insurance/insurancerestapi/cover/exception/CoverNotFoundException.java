package com.kalin.insurance.insurancerestapi.cover.exception;

/**
 * Exception thrown when cover is not found by ID.
 */
public class CoverNotFoundException extends RuntimeException{
    public CoverNotFoundException(Long id) {
        super("Cover with id: " + id + " was not found.");
    }
}
