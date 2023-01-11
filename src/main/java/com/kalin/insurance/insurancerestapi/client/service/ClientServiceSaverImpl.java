package com.kalin.insurance.insurancerestapi.client.service;

import com.kalin.insurance.insurancerestapi.address.Address;
import com.kalin.insurance.insurancerestapi.address.type.AddressType;
import com.kalin.insurance.insurancerestapi.address.type.service.AddressTypeServiceGetter;
import com.kalin.insurance.insurancerestapi.client.Client;
import com.kalin.insurance.insurancerestapi.client.ClientRepository;
import com.kalin.insurance.insurancerestapi.model.service.ServiceSaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service used to saving client to database.
 */
@Service
public class ClientServiceSaverImpl implements ServiceSaver<Client> {
    private final AddressTypeServiceGetter addressTypeServiceGetter;
    private final ClientRepository clientRepository;

    @Autowired
    public ClientServiceSaverImpl(AddressTypeServiceGetter addressTypeServiceGetter, ClientRepository clientRepository) {
        this.addressTypeServiceGetter = addressTypeServiceGetter;
        this.clientRepository = clientRepository;
    }

    @Override
    public Client save(Client client) {
        // findAddressTypeByType throws exception when address type is not found.
        // The purpose of getting and setting address type is to not make a new entry in database, but to use existing one
        AddressType addressType = addressTypeServiceGetter.findAddressTypeByType(client.getAddress().getAddressType().getType());
        Address address = client.getAddress();
        address.setAddressType(addressType);
        return clientRepository.save(client);
    }
}
