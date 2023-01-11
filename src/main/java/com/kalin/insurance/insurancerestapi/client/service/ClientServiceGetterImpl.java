package com.kalin.insurance.insurancerestapi.client.service;

import com.kalin.insurance.insurancerestapi.client.Client;
import com.kalin.insurance.insurancerestapi.client.ClientRepository;
import com.kalin.insurance.insurancerestapi.client.exception.ClientNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service used to getting clients from database.
 */
@Service
public class ClientServiceGetterImpl implements ClientServiceGetter {
    private final ClientRepository clientRepository;

    @Autowired
    public ClientServiceGetterImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Client findById(Long id) {
        return getClientById(id);
    }

    private Client getClientById(Long id) throws ClientNotFoundException {
        return clientRepository.findById(id).orElseThrow(() -> new ClientNotFoundException(id));
    }
}
