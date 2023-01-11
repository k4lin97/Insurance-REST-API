package com.kalin.insurance.insurancerestapi.client.controller;

import com.kalin.insurance.insurancerestapi.client.Client;
import com.kalin.insurance.insurancerestapi.client.service.ClientServiceGetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Rest controller that handles clients GET requests. Used for getting clients.
 */
@RestController
@RequestMapping("/clients")
public class ClientControllerGetter {
    private final ClientServiceGetter clientServiceGetter;

    @Autowired
    public ClientControllerGetter(ClientServiceGetter clientServiceGetter) {
        this.clientServiceGetter = clientServiceGetter;
    }

    @GetMapping()
    public List<Client> findAll() {
        return clientServiceGetter.findAll();
    }

    @GetMapping("/{id}")
    public Client findById(@PathVariable("id") Long id) {
        return clientServiceGetter.findById(id);
    }
}
