package com.kalin.insurance.insurancerestapi.address.type.controller;

import com.kalin.insurance.insurancerestapi.model.service.ServiceDeleter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

/**
 * Rest controller that handles address type DELETE requests. Used for deleting address types.
 */
@RestController
@RequestMapping("/addresses/types")
public class AddressTypeControllerDeleter {
    private final ServiceDeleter addressTypeServiceDeleter;

    @Autowired
    public AddressTypeControllerDeleter(@Qualifier("addressTypeServiceDelete") ServiceDeleter addressTypeServiceDeleter) {
        this.addressTypeServiceDeleter = addressTypeServiceDeleter;
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        addressTypeServiceDeleter.deleteById(id);
    }
}
