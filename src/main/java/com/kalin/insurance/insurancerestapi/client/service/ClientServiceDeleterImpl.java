package com.kalin.insurance.insurancerestapi.client.service;

import com.kalin.insurance.insurancerestapi.client.ClientRepository;
import com.kalin.insurance.insurancerestapi.model.checker.ExistenceCheckerById;
import com.kalin.insurance.insurancerestapi.model.service.ServiceDeleter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Service used to deleting clients from database.
 */
@Service
@Qualifier("clientServiceDeleter")
public class ClientServiceDeleterImpl implements ServiceDeleter {
    private final ClientRepository clientRepository;
    private final ExistenceCheckerById existenceCheckerById;

    @Autowired
    public ClientServiceDeleterImpl(ClientRepository clientRepository,
                                    @Qualifier("clientExistenceCheckerById") ExistenceCheckerById existenceCheckerById) {
        this.clientRepository = clientRepository;
        this.existenceCheckerById = existenceCheckerById;
    }

    @Override
    public void deleteById(Long id) {
        existenceCheckerById.checkIfAlreadyExists(id);
        clientRepository.deleteById(id);
    }
}
