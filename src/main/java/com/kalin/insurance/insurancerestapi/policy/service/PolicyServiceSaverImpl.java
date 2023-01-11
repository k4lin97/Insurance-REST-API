package com.kalin.insurance.insurancerestapi.policy.service;

import com.kalin.insurance.insurancerestapi.address.type.AddressType;
import com.kalin.insurance.insurancerestapi.address.type.service.AddressTypeServiceGetter;
import com.kalin.insurance.insurancerestapi.model.service.ServiceSaver;
import com.kalin.insurance.insurancerestapi.policy.Policy;
import com.kalin.insurance.insurancerestapi.policy.PolicyRepository;
import com.kalin.insurance.insurancerestapi.policy.checker.PolicyConsistencyChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service used to saving policy to database.
 */
@Service
public class PolicyServiceSaverImpl implements ServiceSaver<Policy> {
    private final PolicyConsistencyChecker policyConsistencyChecker;
    private final AddressTypeServiceGetter addressTypeServiceGetter;
    private final PolicyRepository policyRepository;

    @Autowired
    public PolicyServiceSaverImpl(PolicyConsistencyChecker policyConsistencyChecker, AddressTypeServiceGetter addressTypeServiceGetter, PolicyRepository policyRepository) {
        this.policyConsistencyChecker = policyConsistencyChecker;
        this.addressTypeServiceGetter = addressTypeServiceGetter;
        this.policyRepository = policyRepository;
    }

    @Override
    public Policy save(Policy policy) {
        policyConsistencyChecker.checkPolicyConsistency(policy);
        AddressType addressType = addressTypeServiceGetter.findAddressTypeByType(policy.getClient().getAddress().getAddressType().getType());
        policy.getClient().getAddress().setAddressType(addressType);

        return policyRepository.save(policy);
    }
}
