package com.kalin.insurance.insurancerestapi.address.type.service;

import com.kalin.insurance.insurancerestapi.address.type.AddressType;
import com.kalin.insurance.insurancerestapi.address.type.AddressTypeRepository;
import com.kalin.insurance.insurancerestapi.address.type.checker.AddressTypeExistenceCheckerByAddressType;
import com.kalin.insurance.insurancerestapi.model.service.ServiceSaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service used to saving address type to database.
 */
@Service
public class AddressTypeServiceSaverImpl implements ServiceSaver<AddressType> {
    private final AddressTypeRepository addressTypeRepository;
    private final AddressTypeExistenceCheckerByAddressType addressTypeExistenceCheckerByAddressType;

    @Autowired
    public AddressTypeServiceSaverImpl(AddressTypeRepository addressTypeRepository, AddressTypeExistenceCheckerByAddressType addressTypeExistenceCheckerByAddressType) {
        this.addressTypeRepository = addressTypeRepository;
        this.addressTypeExistenceCheckerByAddressType = addressTypeExistenceCheckerByAddressType;
    }

    @Override
    public AddressType save(AddressType addressType) {
        addressTypeExistenceCheckerByAddressType.checkIfAddressTypeAlreadyExists(addressType);
        return addressTypeRepository.save(addressType);
    }
}
