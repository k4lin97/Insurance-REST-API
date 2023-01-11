package com.kalin.insurance.insurancerestapi.address.type.service;

import com.kalin.insurance.insurancerestapi.address.type.AddressType;
import com.kalin.insurance.insurancerestapi.address.type.AddressTypeRepository;
import com.kalin.insurance.insurancerestapi.address.type.checker.AddressTypeExistenceCheckerByAddressType;
import com.kalin.insurance.insurancerestapi.address.type.exception.AddressTypeNotFoundException;
import com.kalin.insurance.insurancerestapi.model.service.ServiceUpdater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service used to updating address type by ID. It is not possible to make duplicates in the database.
 * Although service allows to change the type for the same type.
 */
@Service
public class AddressTypeServiceUpdaterImpl implements ServiceUpdater<AddressType> {
    private final AddressTypeRepository addressTypeRepository;
    private final AddressTypeExistenceCheckerByAddressType addressTypeExistenceCheckerByAddressType;

    @Autowired
    public AddressTypeServiceUpdaterImpl(AddressTypeRepository addressTypeRepository, AddressTypeExistenceCheckerByAddressType addressTypeExistenceCheckerByAddressType) {
        this.addressTypeRepository = addressTypeRepository;
        this.addressTypeExistenceCheckerByAddressType = addressTypeExistenceCheckerByAddressType;
    }

    @Override
    public AddressType update(AddressType addressType, Long id) {
        Optional<AddressType> addressTypeOptional = addressTypeRepository.findById(id);

        // Not using existence checker here because, address type is needed below
        if (addressTypeOptional.isPresent()) {
            checkIfAddressTypeAlreadyExists(addressType, addressTypeOptional.get());
            addressType.setId(id);
            return addressTypeRepository.save(addressType);
        }

        // Address type was not found in a database
        throw new AddressTypeNotFoundException(id);
    }

    private void checkIfAddressTypeAlreadyExists(AddressType addressType, AddressType existingAddressType) {
        // We can change current name for the same name. Although we cannot make duplicates in a system
        if (!existingAddressType.equals(addressType)) {
            addressTypeExistenceCheckerByAddressType.checkIfAddressTypeAlreadyExists(addressType);
        }
    }
}
