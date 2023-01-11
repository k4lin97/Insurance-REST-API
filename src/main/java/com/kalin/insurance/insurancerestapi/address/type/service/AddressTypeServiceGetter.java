package com.kalin.insurance.insurancerestapi.address.type.service;

import com.kalin.insurance.insurancerestapi.address.type.AddressType;
import com.kalin.insurance.insurancerestapi.address.type.exception.AddressTypeNotFoundException;
import com.kalin.insurance.insurancerestapi.model.service.ServiceGetterAll;
import com.kalin.insurance.insurancerestapi.model.service.ServiceGetterById;

/**
 * Defines methods needed to get address types from database.
 */
public interface AddressTypeServiceGetter extends ServiceGetterAll<AddressType>, ServiceGetterById<AddressType> {
    AddressType findAddressTypeByType(String type) throws AddressTypeNotFoundException;
}
