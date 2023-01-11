package com.kalin.insurance.insurancerestapi.address.type.checker;

import com.kalin.insurance.insurancerestapi.address.AddressRepository;
import com.kalin.insurance.insurancerestapi.address.type.exception.AddressTypeToAddressAssignedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Checks if address type, specified by ID, is assigned to any address.
 */
@Component
public class AddressAssignmentCheckerImpl implements AddressAssignmentChecker {
    private final AddressRepository addressRepository;

    @Autowired
    public AddressAssignmentCheckerImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public void checkIfAddressTypeIsAssignedToAddress(Long id) throws AddressTypeToAddressAssignedException {
        if (addressRepository.countAddressesByAddressTypeId(id) > 0) {
            throw new AddressTypeToAddressAssignedException(id);
        }
    }
}
