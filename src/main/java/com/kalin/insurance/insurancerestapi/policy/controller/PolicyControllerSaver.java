package com.kalin.insurance.insurancerestapi.policy.controller;

import com.kalin.insurance.insurancerestapi.model.service.ServiceSaver;
import com.kalin.insurance.insurancerestapi.policy.Policy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Rest controller that handles policy POST requests. Used for creating new policies.
 */
@RestController
@RequestMapping("/policies")
public class PolicyControllerSaver {
    private final ServiceSaver<Policy> policyServiceSaver;

    @Autowired
    public PolicyControllerSaver(ServiceSaver<Policy> policyServiceSaver) {
        this.policyServiceSaver = policyServiceSaver;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Policy save(@Valid @RequestBody Policy policy) {
        return policyServiceSaver.save(policy);
    }
}
