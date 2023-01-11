package com.kalin.insurance.insurancerestapi.policy.service;

import com.kalin.insurance.insurancerestapi.model.service.ServiceGetterAll;
import com.kalin.insurance.insurancerestapi.model.service.ServiceGetterById;
import com.kalin.insurance.insurancerestapi.model.service.ServiceGetterReferenceById;
import com.kalin.insurance.insurancerestapi.policy.Policy;

/**
 * Defines methods needed to get policy from database.
 */
public interface PolicyServiceGetter extends ServiceGetterAll<Policy>, ServiceGetterById<Policy>, ServiceGetterReferenceById<Policy> {
}
