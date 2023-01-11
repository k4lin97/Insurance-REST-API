package com.kalin.insurance.insurancerestapi.address.controller;

import com.kalin.insurance.insurancerestapi.address.Address;
import com.kalin.insurance.insurancerestapi.address.service.AddressServiceGetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Rest controller that handles address GET requests. Used for getting addresses.
 */
@RestController
@RequestMapping("/addresses")
public class AddressControllerGetter {
    private final AddressServiceGetter addressServiceGetter;

    @Autowired
    public AddressControllerGetter(AddressServiceGetter addressServiceGetter) {
        this.addressServiceGetter = addressServiceGetter;
    }

    @GetMapping()
    public List<Address> findAll() {
        return addressServiceGetter.findAll();
    }

    @GetMapping("/{id}")
    public Address findById(@PathVariable("id") Long id) {
        return addressServiceGetter.findById(id);
    }
}
