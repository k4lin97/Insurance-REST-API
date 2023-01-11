package com.kalin.insurance.insurancerestapi.policy.controller;

import com.kalin.insurance.insurancerestapi.model.service.ServiceDeleter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

/**
 * Rest controller that handles policy DELETE requests. Used for deleting policies.
 */
@RestController
@RequestMapping("/policies")
public class PolicyControllerDeleter {
    private final ServiceDeleter policyServiceDeleter;

    @Autowired
    public PolicyControllerDeleter(@Qualifier("policyServiceDeleterImpl") ServiceDeleter policyServiceDeleter) {
        this.policyServiceDeleter = policyServiceDeleter;
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Long id) {
        policyServiceDeleter.deleteById(id);
    }
}
