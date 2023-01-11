package com.kalin.insurance.insurancerestapi.address.service;

import com.kalin.insurance.insurancerestapi.address.AddressRepository;
import com.kalin.insurance.insurancerestapi.model.checker.ExistenceCheckerById;
import com.kalin.insurance.insurancerestapi.model.service.ServiceDeleter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Service used to deleting addresses from database.
 */
@Service
@Qualifier("addressServiceDelete")
public class AddressServiceDeleterImpl implements ServiceDeleter {
    private final AddressRepository addressRepository;
    private final ExistenceCheckerById existenceCheckerById;

    @Autowired
    public AddressServiceDeleterImpl(AddressRepository addressRepository,
                                     @Qualifier("addressExistenceCheckerById") ExistenceCheckerById existenceCheckerById) {
        this.addressRepository = addressRepository;
        this.existenceCheckerById = existenceCheckerById;
    }

    @Override
    public void deleteById(Long id) {
        existenceCheckerById.checkIfAlreadyExists(id);
        addressRepository.deleteById(id);
    }
}
