package com.kalin.insurance.insurancerestapi.policy.checker;

import com.kalin.insurance.insurancerestapi.policy.Policy;
import com.kalin.insurance.insurancerestapi.policy.exception.PolicyNotConsistentException;

/**
 * Defines methods needed to check if the policy is consistent.
 */
public interface PolicyConsistencyChecker {
    void checkPolicyConsistency(Policy policy) throws PolicyNotConsistentException;
}
