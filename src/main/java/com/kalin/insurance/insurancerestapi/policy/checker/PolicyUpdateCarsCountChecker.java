package com.kalin.insurance.insurancerestapi.policy.checker;

import com.kalin.insurance.insurancerestapi.policy.Policy;
import com.kalin.insurance.insurancerestapi.policy.exception.PolicyCarsCountNotEqualException;

/**
 * Defines methods needed to check if the existing policy has the same number of cars as the new policy.
 */
public interface PolicyUpdateCarsCountChecker {
    void checkIfExistingAndNewCarsCountIsEqual(Policy existingPolicy, Policy newPolicy) throws PolicyCarsCountNotEqualException;
}
