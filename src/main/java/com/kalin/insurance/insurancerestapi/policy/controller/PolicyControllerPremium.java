package com.kalin.insurance.insurancerestapi.policy.controller;

import com.kalin.insurance.insurancerestapi.policy.Policy;
import com.kalin.insurance.insurancerestapi.policy.service.PolicyServicePremium;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Rest controller that handles GET requests to calculate given policy's premium.
 */
@RestController
@RequestMapping("/policies")
public class PolicyControllerPremium {
    private final PolicyServicePremium policyServicePremium;

    @Autowired
    public PolicyControllerPremium(PolicyServicePremium policyServicePremium) {
        this.policyServicePremium = policyServicePremium;
    }

    @GetMapping("/{id}/premium")
    public Policy calculatePolicyPremium(@PathVariable("id") Long id) {
        return policyServicePremium.calculatePolicyPremium(id);
    }
}
