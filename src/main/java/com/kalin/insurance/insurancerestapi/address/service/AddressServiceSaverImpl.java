package com.kalin.insurance.insurancerestapi.address.service;

import com.kalin.insurance.insurancerestapi.address.Address;
import com.kalin.insurance.insurancerestapi.address.AddressRepository;
import com.kalin.insurance.insurancerestapi.address.type.AddressType;
import com.kalin.insurance.insurancerestapi.address.type.service.AddressTypeServiceGetter;
import com.kalin.insurance.insurancerestapi.model.service.ServiceSaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service used to saving address to database.
 */
@Service
public class AddressServiceSaverImpl implements ServiceSaver<Address> {
    private final AddressTypeServiceGetter addressTypeServiceGetter;
    private final AddressRepository addressRepository;

    @Autowired
    public AddressServiceSaverImpl(AddressTypeServiceGetter addressTypeServiceGetter, AddressRepository addressRepository) {
        this.addressTypeServiceGetter = addressTypeServiceGetter;
        this.addressRepository = addressRepository;
    }

    @Override
    public Address save(Address address) {
        // Get the address type by the type specified in address. If it is not found it throws an exception
        AddressType addressType = addressTypeServiceGetter.findAddressTypeByType(address.getAddressType().getType());
        address.setAddressType(addressType);
        return addressRepository.save(address);
    }
}
