package com.kalin.insurance.insurancerestapi.client.controller;

import com.kalin.insurance.insurancerestapi.client.Client;
import com.kalin.insurance.insurancerestapi.model.service.ServiceUpdater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Rest controller that handles client PUT requests. Used to update clients.
 */
@RestController
@RequestMapping("/clients")
public class ClientControllerUpdater {
    private final ServiceUpdater<Client> clientServiceUpdater;

    @Autowired
    public ClientControllerUpdater(ServiceUpdater<Client> clientServiceUpdater) {
        this.clientServiceUpdater = clientServiceUpdater;
    }

    @PutMapping("/{id}")
    public Client update(@Valid @RequestBody Client client, @PathVariable("id") Long id) {
        return clientServiceUpdater.update(client, id);
    }
}
