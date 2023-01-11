package com.kalin.insurance.insurancerestapi.address.service;

import com.kalin.insurance.insurancerestapi.address.Address;
import com.kalin.insurance.insurancerestapi.model.service.ServiceGetterAll;
import com.kalin.insurance.insurancerestapi.model.service.ServiceGetterById;

/**
 * Defines methods needed to get addresses from database.
 */
public interface AddressServiceGetter extends ServiceGetterAll<Address>, ServiceGetterById<Address> {
}
