package com.kalin.insurance.insurancerestapi.policy.service;

import com.kalin.insurance.insurancerestapi.model.service.ServiceSaver;
import com.kalin.insurance.insurancerestapi.policy.Policy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Calculates given policy's premium.
 */
@Service
public class PolicyServicePremiumImpl implements PolicyServicePremium {
    private final PolicyServiceGetter policyServiceGetter;
    private final ServiceSaver<Policy> policyServiceSaver;

    @Autowired
    public PolicyServicePremiumImpl(PolicyServiceGetter policyServiceGetter, ServiceSaver<Policy> policyServiceSaver) {
        this.policyServiceGetter = policyServiceGetter;
        this.policyServiceSaver = policyServiceSaver;
    }

    @Override
    public Policy calculatePolicyPremium(Long id) {
        Policy policy = policyServiceGetter.findById(id);
        policy.calculateTotalPremium();
        return policyServiceSaver.save(policy);
    }
}
