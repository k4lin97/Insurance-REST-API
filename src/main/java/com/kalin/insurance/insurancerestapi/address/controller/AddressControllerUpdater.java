package com.kalin.insurance.insurancerestapi.address.controller;

import com.kalin.insurance.insurancerestapi.address.Address;
import com.kalin.insurance.insurancerestapi.model.service.ServiceUpdater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Rest controller that handles address PUT requests. Used to update addresses.
 */
@RestController
@RequestMapping("/addresses")
public class AddressControllerUpdater {
    private final ServiceUpdater<Address> addressServiceUpdater;

    @Autowired
    public AddressControllerUpdater(ServiceUpdater<Address> addressServiceUpdater) {
        this.addressServiceUpdater = addressServiceUpdater;
    }

    @PutMapping("/{id}")
    public Address update(@Valid @RequestBody Address address, @PathVariable("id") Long id) {
        return addressServiceUpdater.update(address, id);
    }
}
