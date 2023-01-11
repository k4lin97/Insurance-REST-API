package com.kalin.insurance.insurancerestapi.car.assigner;

import com.kalin.insurance.insurancerestapi.car.Car;
import com.kalin.insurance.insurancerestapi.policy.Policy;
import com.kalin.insurance.insurancerestapi.policy.service.PolicyServiceGetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Assigns policy to a car.
 */
@Component
public class CarPolicyAssignerImpl implements CarPolicyAssigner {
    private final PolicyServiceGetter policyServiceGetter;

    @Autowired
    public CarPolicyAssignerImpl(PolicyServiceGetter policyServiceGetter) {
        this.policyServiceGetter = policyServiceGetter;
    }

    @Override
    public void assignPolicyToCar(Car car) {
        Policy policy = policyServiceGetter.getReferenceById(car.getPolicy().getId());
        car.setPolicy(policy);
    }
}
