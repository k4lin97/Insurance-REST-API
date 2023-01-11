package com.kalin.insurance.insurancerestapi.client.controller;

import com.kalin.insurance.insurancerestapi.client.Client;
import com.kalin.insurance.insurancerestapi.model.service.ServiceSaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Rest controller that handles clients POST requests. Used for creating new clients.
 */
@RestController
@RequestMapping("/clients")
public class ClientControllerSaver {
    private final ServiceSaver<Client> clientServiceSaver;

    @Autowired
    public ClientControllerSaver(ServiceSaver<Client> clientServiceSaver) {
        this.clientServiceSaver = clientServiceSaver;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Client save(@Valid @RequestBody Client client) {
        return clientServiceSaver.save(client);
    }
}
