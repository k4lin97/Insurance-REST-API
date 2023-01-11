package com.kalin.insurance.insurancerestapi.policy.service;

import com.kalin.insurance.insurancerestapi.car.assigner.CarPolicyAssigner;
import com.kalin.insurance.insurancerestapi.model.service.ServiceSaver;
import com.kalin.insurance.insurancerestapi.model.service.ServiceUpdater;
import com.kalin.insurance.insurancerestapi.policy.Policy;
import com.kalin.insurance.insurancerestapi.policy.PolicyRepository;
import com.kalin.insurance.insurancerestapi.policy.checker.PolicyConsistencyChecker;
import com.kalin.insurance.insurancerestapi.policy.checker.PolicyUpdateCarsCountChecker;
import com.kalin.insurance.insurancerestapi.policy.exception.PolicyNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service used to updating, if present in database, policy by ID.
 */
@Service
public class PolicyServiceUpdaterImpl implements ServiceUpdater<Policy> {
    private final PolicyRepository policyRepository;
    private final ServiceSaver<Policy> policyServiceSaver;
    private final PolicyConsistencyChecker policyConsistencyChecker;
    private final PolicyUpdateCarsCountChecker policyUpdateCarsCountChecker;
    private final CarPolicyAssigner carPolicyAssigner;

    @Autowired
    public PolicyServiceUpdaterImpl(PolicyRepository policyRepository,
                                    ServiceSaver<Policy> policyServiceSaver,
                                    PolicyConsistencyChecker policyConsistencyChecker,
                                    PolicyUpdateCarsCountChecker policyUpdateCarsCountChecker,
                                    CarPolicyAssigner carPolicyAssigner) {
        this.policyRepository = policyRepository;
        this.policyServiceSaver = policyServiceSaver;
        this.policyConsistencyChecker = policyConsistencyChecker;
        this.policyUpdateCarsCountChecker = policyUpdateCarsCountChecker;
        this.carPolicyAssigner = carPolicyAssigner;
    }

    @Override
    public Policy update(Policy policy, Long id) {
        policyConsistencyChecker.checkPolicyConsistency(policy);

        // Not using policy existence checker here, because we need optional policy afterwards
        Optional<Policy> optionalPolicy = policyRepository.findById(id);
        if (optionalPolicy.isPresent()) {
            policyUpdateCarsCountChecker.checkIfExistingAndNewCarsCountIsEqual(optionalPolicy.get(), policy);

            updateIdsNeededToUpdatePolicy(policy, optionalPolicy.get(), id);
            assignCarsAndPolicy(policy, optionalPolicy.get());

            return policyServiceSaver.save(policy);
        }
        throw new PolicyNotFoundException(id);
    }

    private void updateIdsNeededToUpdatePolicy(Policy policy, Policy existingPolicy, Long id) {
        policy.setId(id);
        policy.getClient().getAddress()
                .setId(existingPolicy.getClient().getAddress().getId());
        policy.getClient()
                .setId(existingPolicy.getClient().getId());
    }

    private void assignCarsAndPolicy(Policy policy, Policy existingPolicy) {
        for (int i = 0; i < policy.getCars().size(); i++) {
            carPolicyAssigner.assignPolicyToCar(policy.getCars().get(i));
            policy.getCars().get(i).setId(existingPolicy.getCars().get(i).getId());
        }
    }
}
