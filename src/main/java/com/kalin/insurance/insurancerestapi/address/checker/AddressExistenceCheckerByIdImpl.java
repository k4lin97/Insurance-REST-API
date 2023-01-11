package com.kalin.insurance.insurancerestapi.address.checker;

import com.kalin.insurance.insurancerestapi.address.Address;
import com.kalin.insurance.insurancerestapi.address.AddressRepository;
import com.kalin.insurance.insurancerestapi.address.exception.AddressNotFoundException;
import com.kalin.insurance.insurancerestapi.model.checker.ExistenceCheckerById;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Checks if the address exists by given ID.
 */
@Component
@Qualifier("addressExistenceCheckerById")
public class AddressExistenceCheckerByIdImpl implements ExistenceCheckerById {
    private final AddressRepository addressRepository;

    @Autowired
    public AddressExistenceCheckerByIdImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public void checkIfAlreadyExists(Long id) throws AddressNotFoundException {
        Optional<Address> addressOptional = addressRepository.findById(id);
        if (addressOptional.isEmpty()) {
            throw new AddressNotFoundException(id);
        }
    }
}
