package com.kalin.insurance.insurancerestapi.policy.exception;

/**
 * Exception thrown when existing policy has different number of cars than the new policy.
 */
public class PolicyCarsCountNotEqualException extends RuntimeException {
    public PolicyCarsCountNotEqualException(int existingCarsCount, int newCarsCount) {
        super("Policy has " + existingCarsCount + " cars, yet you have specified " + newCarsCount + " cars.");
    }
}
