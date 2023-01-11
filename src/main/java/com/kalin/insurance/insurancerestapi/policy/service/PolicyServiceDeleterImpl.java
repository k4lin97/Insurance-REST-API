package com.kalin.insurance.insurancerestapi.policy.service;

import com.kalin.insurance.insurancerestapi.model.checker.ExistenceCheckerById;
import com.kalin.insurance.insurancerestapi.model.service.ServiceDeleter;
import com.kalin.insurance.insurancerestapi.policy.PolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Service used to deleting policies from database.
 */
@Service
@Qualifier("policyServiceDeleter")
public class PolicyServiceDeleterImpl implements ServiceDeleter {
    private final ExistenceCheckerById existenceCheckerById;
    private final PolicyRepository policyRepository;

    @Autowired
    public PolicyServiceDeleterImpl(@Qualifier("policyExistenceCheckerById") ExistenceCheckerById existenceCheckerById,
                                    PolicyRepository policyRepository) {
        this.existenceCheckerById = existenceCheckerById;
        this.policyRepository = policyRepository;
    }

    @Override
    public void deleteById(Long id) {
        existenceCheckerById.checkIfAlreadyExists(id);
        policyRepository.deleteById(id);
    }
}
