package com.kalin.insurance.insurancerestapi.address.service;

import com.kalin.insurance.insurancerestapi.address.Address;
import com.kalin.insurance.insurancerestapi.model.checker.ExistenceCheckerById;
import com.kalin.insurance.insurancerestapi.model.service.ServiceSaver;
import com.kalin.insurance.insurancerestapi.model.service.ServiceUpdater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Service used to updating, if present in database, address by ID.
 */
@Service
public class AddressServiceUpdaterImpl implements ServiceUpdater<Address> {
    private final ServiceSaver<Address> addressServiceSaver;
    private final ExistenceCheckerById existenceCheckerById;

    @Autowired
    public AddressServiceUpdaterImpl(ServiceSaver<Address> addressServiceSaver,
                                     @Qualifier("addressExistenceCheckerById") ExistenceCheckerById existenceCheckerById) {
        this.addressServiceSaver = addressServiceSaver;
        this.existenceCheckerById = existenceCheckerById;
    }

    @Override
    public Address update(Address address, Long id) {
        existenceCheckerById.checkIfAlreadyExists(id);
        address.setId(id);
        return addressServiceSaver.save(address);
    }
}
