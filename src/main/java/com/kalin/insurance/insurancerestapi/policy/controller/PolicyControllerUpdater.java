package com.kalin.insurance.insurancerestapi.policy.controller;

import com.kalin.insurance.insurancerestapi.model.service.ServiceUpdater;
import com.kalin.insurance.insurancerestapi.policy.Policy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Rest controller that handles policy PUT requests. Used to update policies.
 */
@RestController
@RequestMapping("/policies")
public class PolicyControllerUpdater {
    private final ServiceUpdater<Policy> policyServiceUpdater;

    @Autowired
    public PolicyControllerUpdater(ServiceUpdater<Policy> policyServiceUpdater) {
        this.policyServiceUpdater = policyServiceUpdater;
    }

    @PutMapping("/{id}")
    public Policy update(@Valid @RequestBody Policy policy, @PathVariable("id") Long id) {
        return policyServiceUpdater.update(policy, id);
    }
}
