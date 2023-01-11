package com.kalin.insurance.insurancerestapi.address.service;

import com.kalin.insurance.insurancerestapi.address.Address;
import com.kalin.insurance.insurancerestapi.address.AddressRepository;
import com.kalin.insurance.insurancerestapi.address.exception.AddressNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service used to getting address from database.
 */
@Service
public class AddressServiceGetterImpl implements AddressServiceGetter {
    private final AddressRepository addressRepository;

    @Autowired
    public AddressServiceGetterImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public List<Address> findAll() {
        return addressRepository.findAll();
    }

    @Override
    public Address findById(Long id) {
        return getAddressById(id);
    }

    private Address getAddressById(Long id) throws AddressNotFoundException {
        return addressRepository.findById(id).orElseThrow(() -> new AddressNotFoundException(id));
    }
}
