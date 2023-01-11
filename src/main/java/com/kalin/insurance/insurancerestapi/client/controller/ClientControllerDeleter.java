package com.kalin.insurance.insurancerestapi.client.controller;

import com.kalin.insurance.insurancerestapi.model.service.ServiceDeleter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

/**
 * Rest controller that handles client DELETE requests. Used for deleting clients.
 */
@RestController
@RequestMapping("/clients")
public class ClientControllerDeleter {
    private final ServiceDeleter clientServiceDeleter;

    @Autowired
    public ClientControllerDeleter(@Qualifier("clientServiceDeleterImpl") ServiceDeleter clientServiceDeleter) {
        this.clientServiceDeleter = clientServiceDeleter;
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Long id) {
        clientServiceDeleter.deleteById(id);
    }

}
