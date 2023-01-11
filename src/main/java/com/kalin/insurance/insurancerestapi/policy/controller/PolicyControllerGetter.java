package com.kalin.insurance.insurancerestapi.policy.controller;

import com.kalin.insurance.insurancerestapi.policy.Policy;
import com.kalin.insurance.insurancerestapi.policy.service.PolicyServiceGetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Rest controller that handles policy GET requests. Used for getting policies.
 */
@RestController
@RequestMapping("/policies")
public class PolicyControllerGetter {
    private final PolicyServiceGetter policyServiceGetter;

    @Autowired
    public PolicyControllerGetter(PolicyServiceGetter policyServiceGetter) {
        this.policyServiceGetter = policyServiceGetter;
    }

    @GetMapping()
    public List<Policy> findAll() {
        return policyServiceGetter.findAll();
    }

    @GetMapping("/{id}")
    public Policy findById(@PathVariable("id") Long id) {
        return policyServiceGetter.findById(id);
    }
}
