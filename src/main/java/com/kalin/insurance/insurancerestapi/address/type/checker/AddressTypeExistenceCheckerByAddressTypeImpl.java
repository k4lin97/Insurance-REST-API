package com.kalin.insurance.insurancerestapi.address.type.checker;

import com.kalin.insurance.insurancerestapi.address.type.AddressType;
import com.kalin.insurance.insurancerestapi.address.type.AddressTypeRepository;
import com.kalin.insurance.insurancerestapi.address.type.exception.AddressTypeDuplicatedException;
import com.kalin.insurance.insurancerestapi.address.type.exception.AddressTypeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Checks if address type, specified by address type itself, is present in database.
 */
@Component
public class AddressTypeExistenceCheckerByAddressTypeImpl implements AddressTypeExistenceCheckerByAddressType {
    private final AddressTypeRepository addressTypeRepository;

    @Autowired
    public AddressTypeExistenceCheckerByAddressTypeImpl(AddressTypeRepository addressTypeRepository) {
        this.addressTypeRepository = addressTypeRepository;
    }

    @Override
    public void checkIfAddressTypeAlreadyExists(AddressType addressType) throws AddressTypeNotFoundException, AddressTypeDuplicatedException {
        if (addressTypeRepository.countAddressTypesByType(addressType.getType()) == 1) {
            throw new AddressTypeDuplicatedException(addressType.getType());
        }
    }
}
