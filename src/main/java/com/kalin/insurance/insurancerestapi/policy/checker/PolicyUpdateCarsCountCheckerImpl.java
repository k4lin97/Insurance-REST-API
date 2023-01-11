package com.kalin.insurance.insurancerestapi.policy.checker;

import com.kalin.insurance.insurancerestapi.policy.Policy;
import com.kalin.insurance.insurancerestapi.policy.exception.PolicyCarsCountNotEqualException;
import org.springframework.stereotype.Component;

/**
 * Checks if the existing policy has the same number of cars as the new policy.
 */
@Component
public class PolicyUpdateCarsCountCheckerImpl implements PolicyUpdateCarsCountChecker {
    @Override
    public void checkIfExistingAndNewCarsCountIsEqual(Policy existingPolicy, Policy newPolicy) throws PolicyCarsCountNotEqualException {
        int existingCarsCount = existingPolicy.getCars().size();
        int newCarsCount = newPolicy.getCars().size();
        if (existingCarsCount != newCarsCount) {
            throw new PolicyCarsCountNotEqualException(existingCarsCount, newCarsCount);
        }
    }
}
