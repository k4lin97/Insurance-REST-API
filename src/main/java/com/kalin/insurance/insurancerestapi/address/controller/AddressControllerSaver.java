package com.kalin.insurance.insurancerestapi.address.controller;

import com.kalin.insurance.insurancerestapi.address.Address;
import com.kalin.insurance.insurancerestapi.model.service.ServiceSaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Rest controller that handles address POST requests. Used for creating new addresses.
 */
@RestController
@RequestMapping("/addresses")
public class AddressControllerSaver {
    private final ServiceSaver<Address> addressServiceSaver;

    @Autowired
    public AddressControllerSaver(ServiceSaver<Address> addressServiceSaver) {
        this.addressServiceSaver = addressServiceSaver;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Address save(@Valid @RequestBody Address address) {
        return addressServiceSaver.save(address);
    }
}
