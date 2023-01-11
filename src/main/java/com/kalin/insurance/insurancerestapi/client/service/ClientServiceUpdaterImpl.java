package com.kalin.insurance.insurancerestapi.client.service;

import com.kalin.insurance.insurancerestapi.address.Address;
import com.kalin.insurance.insurancerestapi.client.Client;
import com.kalin.insurance.insurancerestapi.client.ClientRepository;
import com.kalin.insurance.insurancerestapi.client.exception.ClientNotFoundException;
import com.kalin.insurance.insurancerestapi.model.service.ServiceSaver;
import com.kalin.insurance.insurancerestapi.model.service.ServiceUpdater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service used to updating, if present in database, client by ID.
 */
@Service
public class ClientServiceUpdaterImpl implements ServiceUpdater<Client> {
    private final ClientRepository clientRepository;
    private final ServiceSaver<Client> clientServiceSaver;

    @Autowired
    public ClientServiceUpdaterImpl(ClientRepository clientRepository, ServiceSaver<Client> clientServiceSaver) {
        this.clientRepository = clientRepository;
        this.clientServiceSaver = clientServiceSaver;
    }

    @Override
    public Client update(Client client, Long id) {
        // Not using ClientExistenceChecker because optionalClient is used afterwards
        Optional<Client> optionalClient = clientRepository.findById(id);

        if (optionalClient.isPresent()) {
            client.setId(id);

            // Make the address update as well
            Address address = client.getAddress();
            address.setId(optionalClient.get().getAddress().getId());
            client.setAddress(address);
            return clientServiceSaver.save(client);
        }

        throw new ClientNotFoundException(id);
    }
}
