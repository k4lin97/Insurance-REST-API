package com.kalin.insurance.insurancerestapi.address.type.controller;

import com.kalin.insurance.insurancerestapi.address.type.AddressType;
import com.kalin.insurance.insurancerestapi.model.service.ServiceUpdater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Rest controller that handles address type PUT requests. Used to update address types.
 */
@RestController
@RequestMapping("/addresses/types")
public class AddressTypeControllerUpdater {
    private final ServiceUpdater<AddressType> addressTypeServiceUpdater;

    @Autowired
    public AddressTypeControllerUpdater(ServiceUpdater<AddressType> addressTypeServiceUpdater) {
        this.addressTypeServiceUpdater = addressTypeServiceUpdater;
    }

    @PutMapping("/{id}")
    public AddressType update(@Valid @RequestBody AddressType addressType, @PathVariable("id") Long id) {
        return addressTypeServiceUpdater.update(addressType, id);
    }
}
