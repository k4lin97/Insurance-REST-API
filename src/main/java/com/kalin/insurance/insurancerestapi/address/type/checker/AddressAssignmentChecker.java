package com.kalin.insurance.insurancerestapi.address.type.checker;

import com.kalin.insurance.insurancerestapi.address.type.exception.AddressTypeToAddressAssignedException;

/**
 * Defines methods needed to check if address type, specified by ID, is assigned to any address.
 */
public interface AddressAssignmentChecker {
    void checkIfAddressTypeIsAssignedToAddress(Long id) throws AddressTypeToAddressAssignedException;
}
