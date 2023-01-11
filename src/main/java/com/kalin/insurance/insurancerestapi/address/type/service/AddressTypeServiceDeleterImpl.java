package com.kalin.insurance.insurancerestapi.address.type.service;

import com.kalin.insurance.insurancerestapi.address.type.AddressTypeRepository;
import com.kalin.insurance.insurancerestapi.address.type.checker.AddressAssignmentChecker;
import com.kalin.insurance.insurancerestapi.model.checker.ExistenceCheckerById;
import com.kalin.insurance.insurancerestapi.model.service.ServiceDeleter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Service used to deleting address types from database.
 */
@Service
@Qualifier("addressTypeServiceDelete")
public class AddressTypeServiceDeleterImpl implements ServiceDeleter {
    private final AddressTypeRepository addressTypeRepository;
    private final ExistenceCheckerById existenceCheckerById;
    private final AddressAssignmentChecker addressAssignmentChecker;

    @Autowired
    public AddressTypeServiceDeleterImpl(AddressTypeRepository addressTypeRepository,
                                         @Qualifier("addressTypeExistenceCheckerById") ExistenceCheckerById existenceCheckerById,
                                         AddressAssignmentChecker addressAssignmentChecker) {
        this.addressTypeRepository = addressTypeRepository;
        this.existenceCheckerById = existenceCheckerById;
        this.addressAssignmentChecker = addressAssignmentChecker;
    }

    @Override
    public void deleteById(Long id) {
        existenceCheckerById.checkIfAlreadyExists(id);
        addressAssignmentChecker.checkIfAddressTypeIsAssignedToAddress(id);
        addressTypeRepository.deleteById(id);
    }
}
