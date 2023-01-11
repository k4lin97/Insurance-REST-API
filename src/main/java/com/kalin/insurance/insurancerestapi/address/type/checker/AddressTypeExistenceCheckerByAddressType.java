package com.kalin.insurance.insurancerestapi.address.type.checker;

import com.kalin.insurance.insurancerestapi.address.type.AddressType;
import com.kalin.insurance.insurancerestapi.address.type.exception.AddressTypeDuplicatedException;
import com.kalin.insurance.insurancerestapi.address.type.exception.AddressTypeNotFoundException;

/**
 * Defines methods needed to check if address type is present in a database, given an address type itself.
 */
public interface AddressTypeExistenceCheckerByAddressType {
    void checkIfAddressTypeAlreadyExists(AddressType addressType) throws AddressTypeNotFoundException, AddressTypeDuplicatedException;
}
