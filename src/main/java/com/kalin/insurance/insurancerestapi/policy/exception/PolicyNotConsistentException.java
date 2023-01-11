package com.kalin.insurance.insurancerestapi.policy.exception;

import java.util.List;

/**
 * Exception thrown when the policy is not consistent.
 */
public class PolicyNotConsistentException extends RuntimeException {
    private final List<String> errors;

    public PolicyNotConsistentException(List<String> errors) {
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}
