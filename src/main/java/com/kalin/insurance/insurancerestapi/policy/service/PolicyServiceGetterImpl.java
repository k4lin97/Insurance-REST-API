package com.kalin.insurance.insurancerestapi.policy.service;

import com.kalin.insurance.insurancerestapi.policy.exception.PolicyNotFoundException;
import com.kalin.insurance.insurancerestapi.policy.Policy;
import com.kalin.insurance.insurancerestapi.policy.PolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service used to getting policies from database.
 */
@Service
public class PolicyServiceGetterImpl implements PolicyServiceGetter {
    private final PolicyRepository policyRepository;

    @Autowired
    public PolicyServiceGetterImpl(PolicyRepository policyRepository) {
        this.policyRepository = policyRepository;
    }

    @Override
    public List<Policy> findAll() {
        return policyRepository.findAll();
    }

    @Override
    public Policy findById(Long id) {
        return getPolicyById(id);
    }

    @Override
    public Policy getReferenceById(Long id) {
        return policyRepository.getReferenceById(id);
    }

    private Policy getPolicyById(Long id) throws PolicyNotFoundException {
        return policyRepository.findById(id).orElseThrow(() -> new PolicyNotFoundException(id));
    }
}
