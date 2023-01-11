package com.kalin.insurance.insurancerestapi.policy.exception;

/**
 * Exception thrown when policy is not found by ID.
 */
public class PolicyNotFoundException extends RuntimeException {
    public PolicyNotFoundException(Long id) {
        super("Policy with id: " + id + " was not found.");
    }
}
