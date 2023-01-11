package com.kalin.insurance.insurancerestapi.client.service;

import com.kalin.insurance.insurancerestapi.client.Client;
import com.kalin.insurance.insurancerestapi.model.service.ServiceGetterAll;
import com.kalin.insurance.insurancerestapi.model.service.ServiceGetterById;

/**
 * Defines methods needed to get client from database.
 */
public interface ClientServiceGetter extends ServiceGetterAll<Client>, ServiceGetterById<Client> {
}
