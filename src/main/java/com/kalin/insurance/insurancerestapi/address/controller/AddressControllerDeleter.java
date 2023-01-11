package com.kalin.insurance.insurancerestapi.address.controller;

import com.kalin.insurance.insurancerestapi.model.service.ServiceDeleter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

/**
 * Rest controller that handles address DELETE requests. Used for deleting addresses.
 */
@RestController
@RequestMapping("/addresses")
public class AddressControllerDeleter {
    private final ServiceDeleter addressServiceDeleter;

    @Autowired
    public AddressControllerDeleter(@Qualifier("addressServiceDelete") ServiceDeleter addressServiceDeleter) {
        this.addressServiceDeleter = addressServiceDeleter;
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        addressServiceDeleter.deleteById(id);
    }
}
