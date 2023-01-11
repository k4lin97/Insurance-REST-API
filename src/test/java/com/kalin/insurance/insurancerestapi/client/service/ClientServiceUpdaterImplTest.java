package com.kalin.insurance.insurancerestapi.client.service;

import com.kalin.insurance.insurancerestapi.address.Address;
import com.kalin.insurance.insurancerestapi.address.type.AddressType;
import com.kalin.insurance.insurancerestapi.client.Client;
import com.kalin.insurance.insurancerestapi.client.ClientRepository;
import com.kalin.insurance.insurancerestapi.client.exception.ClientNotFoundException;
import com.kalin.insurance.insurancerestapi.model.service.ServiceSaver;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@SpringBootTest
class ClientServiceUpdaterImplTest {
    private static final Long CLIENT_ID = 1L;

    @InjectMocks
    private ClientServiceUpdaterImpl clientServiceUpdater;
    @Mock
    private ClientRepository clientRepository;
    @Mock
    private ServiceSaver<Client> clientServiceSaver;

    private static Client client;

    @BeforeAll
    static void setup() {
        client = new Client();
        client.setId(CLIENT_ID);

        AddressType addressType = new AddressType();
        addressType.setId(1L);
        addressType.setType("home");

        Address address = new Address();
        address.setId(1L);
        address.setAddressType(addressType);

        client.setAddress(address);
    }

    @Test
    void givenClientIdShouldReturnAndUpdateClient() {
        given(clientRepository.findById(CLIENT_ID)).willReturn(Optional.of(client));
        given(clientServiceSaver.save(client)).willReturn(client);

        Client updatedClient = clientServiceUpdater.update(client, CLIENT_ID);
        assertEquals(client, updatedClient);
        assertEquals(client.getAddress(), updatedClient.getAddress());
        verify(clientServiceSaver).save(client);
    }

    @Test
    void givenNotExistingClientIdShouldThrowClientNotFoundException() {
        given(clientRepository.findById(CLIENT_ID)).willReturn(Optional.empty());
        assertThrows(ClientNotFoundException.class, () -> clientServiceUpdater.update(client, CLIENT_ID));
    }
}