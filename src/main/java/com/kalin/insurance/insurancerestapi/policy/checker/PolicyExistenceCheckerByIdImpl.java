package com.kalin.insurance.insurancerestapi.policy.checker;

import com.kalin.insurance.insurancerestapi.model.checker.ExistenceCheckerById;
import com.kalin.insurance.insurancerestapi.policy.Policy;
import com.kalin.insurance.insurancerestapi.policy.PolicyRepository;
import com.kalin.insurance.insurancerestapi.policy.exception.PolicyNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Checks if the policy, specified by ID, exists in a database.
 */
@Component
@Qualifier("policyExistenceCheckerById")
public class PolicyExistenceCheckerByIdImpl implements ExistenceCheckerById {
    private final PolicyRepository policyRepository;

    @Autowired
    public PolicyExistenceCheckerByIdImpl(PolicyRepository policyRepository) {
        this.policyRepository = policyRepository;
    }

    @Override
    public void checkIfAlreadyExists(Long id) throws PolicyNotFoundException {
        Optional<Policy> optionalPolicy = policyRepository.findById(id);
        if (optionalPolicy.isEmpty()) {
            throw new PolicyNotFoundException(id);
        }
    }
}
