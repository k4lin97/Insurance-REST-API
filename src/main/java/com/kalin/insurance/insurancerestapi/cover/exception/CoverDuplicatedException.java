package com.kalin.insurance.insurancerestapi.cover.exception;

/**
 * Exception thrown when the specified cover is already assigned to car with ID.
 */
public class CoverDuplicatedException extends RuntimeException {
    public CoverDuplicatedException(String cover, Long id) {
        super("Cover: " + cover + " is already added to the car of id: " + id + ".");
    }
}
