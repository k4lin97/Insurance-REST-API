package com.kalin.insurance.insurancerestapi.client.checker;

import com.kalin.insurance.insurancerestapi.client.Client;
import com.kalin.insurance.insurancerestapi.client.ClientRepository;
import com.kalin.insurance.insurancerestapi.client.exception.ClientNotFoundException;
import com.kalin.insurance.insurancerestapi.model.checker.ExistenceCheckerById;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Checks if the client, specified by ID, exists in a database.
 */
@Component
@Qualifier("clientExistenceCheckerById")
public class ClientExistenceCheckerByIdImpl implements ExistenceCheckerById {
    private final ClientRepository clientRepository;

    @Autowired
    public ClientExistenceCheckerByIdImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public void checkIfAlreadyExists(Long id) throws ClientNotFoundException {
        Optional<Client> optionalClient = clientRepository.findById(id);
        if (optionalClient.isEmpty()) {
            throw new ClientNotFoundException(id);
        }
    }
}
