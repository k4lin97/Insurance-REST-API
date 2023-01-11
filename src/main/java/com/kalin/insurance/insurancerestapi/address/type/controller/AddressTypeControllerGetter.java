package com.kalin.insurance.insurancerestapi.address.type.controller;

import com.kalin.insurance.insurancerestapi.address.type.AddressType;
import com.kalin.insurance.insurancerestapi.address.type.service.AddressTypeServiceGetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Rest controller that handles address type GET requests. Used for getting address types.
 */
@RestController
@RequestMapping("/addresses/types")
public class AddressTypeControllerGetter {
    private final AddressTypeServiceGetter addressTypeServiceGetter;

    @Autowired
    public AddressTypeControllerGetter(AddressTypeServiceGetter addressTypeServiceGetter) {
        this.addressTypeServiceGetter = addressTypeServiceGetter;
    }

    @GetMapping()
    public List<AddressType> findAll() {
        return addressTypeServiceGetter.findAll();
    }

    @GetMapping("/{id}")
    public AddressType findById(@PathVariable("id") Long id) {
        return addressTypeServiceGetter.findById(id);
    }
}
