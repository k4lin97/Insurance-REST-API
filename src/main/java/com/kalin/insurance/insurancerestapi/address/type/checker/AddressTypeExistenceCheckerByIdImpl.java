package com.kalin.insurance.insurancerestapi.address.type.checker;

import com.kalin.insurance.insurancerestapi.address.type.AddressType;
import com.kalin.insurance.insurancerestapi.address.type.AddressTypeRepository;
import com.kalin.insurance.insurancerestapi.address.type.exception.AddressTypeNotFoundException;
import com.kalin.insurance.insurancerestapi.model.checker.ExistenceCheckerById;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Checks if address type, specified by ID, is present in database.
 */
@Component
@Qualifier("addressTypeExistenceCheckerById")
public class AddressTypeExistenceCheckerByIdImpl implements ExistenceCheckerById {
    private final AddressTypeRepository addressTypeRepository;

    @Autowired
    public AddressTypeExistenceCheckerByIdImpl(AddressTypeRepository addressTypeRepository) {
        this.addressTypeRepository = addressTypeRepository;
    }

    @Override
    public void checkIfAlreadyExists(Long id) throws AddressTypeNotFoundException {
        Optional<AddressType> addressType = addressTypeRepository.findById(id);
        if (addressType.isEmpty()) {
            throw new AddressTypeNotFoundException(id);
        }
    }
}
