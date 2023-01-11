package com.kalin.insurance.insurancerestapi.address.type.service;

import com.kalin.insurance.insurancerestapi.address.type.AddressType;
import com.kalin.insurance.insurancerestapi.address.type.AddressTypeRepository;
import com.kalin.insurance.insurancerestapi.address.type.exception.AddressTypeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service used to getting address type from database.
 */
@Service
public class AddressTypeServiceGetterImpl implements AddressTypeServiceGetter {
    private final AddressTypeRepository addressTypeRepository;

    @Autowired
    public AddressTypeServiceGetterImpl(AddressTypeRepository addressTypeRepository) {
        this.addressTypeRepository = addressTypeRepository;
    }

    @Override
    public List<AddressType> findAll() {
        return addressTypeRepository.findAll();
    }

    @Override
    public AddressType findById(Long id) {
        return getAddressTypeById(id);
    }

    @Override
    public AddressType findAddressTypeByType(String type) throws AddressTypeNotFoundException {
        return getAddressTypeByType(type);
    }

    private AddressType getAddressTypeById(Long id) throws AddressTypeNotFoundException {
        return addressTypeRepository.findById(id).orElseThrow(() -> new AddressTypeNotFoundException(id));

    }

    private AddressType getAddressTypeByType(String type) throws AddressTypeNotFoundException {
        return addressTypeRepository.findAddressTypeByType(type).orElseThrow(() -> new AddressTypeNotFoundException(type));
    }
}