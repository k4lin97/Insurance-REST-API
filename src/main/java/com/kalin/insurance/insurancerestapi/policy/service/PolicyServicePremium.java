package com.kalin.insurance.insurancerestapi.policy.service;

import com.kalin.insurance.insurancerestapi.policy.Policy;

/**
 * Defines methods needed to calculate policy's premium.
 */
public interface PolicyServicePremium {
    Policy calculatePolicyPremium(Long id);
}
