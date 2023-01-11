package com.kalin.insurance.insurancerestapi.address.type.controller;

import com.kalin.insurance.insurancerestapi.address.type.AddressType;
import com.kalin.insurance.insurancerestapi.model.service.ServiceSaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Rest controller that handles address type POST requests. Used for creating new address types.
 */
@RestController
@RequestMapping("/addresses/types")
public class AddressTypeControllerSaver {
    private final ServiceSaver<AddressType> addressTypeServiceSaver;

    @Autowired
    public AddressTypeControllerSaver(ServiceSaver<AddressType> addressTypeServiceSaver) {
        this.addressTypeServiceSaver = addressTypeServiceSaver;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public AddressType saveAddressType(@Valid @RequestBody AddressType addressType) {
        return addressTypeServiceSaver.save(addressType);
    }
}
