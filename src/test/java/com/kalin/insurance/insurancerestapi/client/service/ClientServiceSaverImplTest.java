package com.kalin.insurance.insurancerestapi.client.service;

import com.kalin.insurance.insurancerestapi.address.Address;
import com.kalin.insurance.insurancerestapi.address.type.AddressType;
import com.kalin.insurance.insurancerestapi.address.type.exception.AddressTypeNotFoundException;
import com.kalin.insurance.insurancerestapi.address.type.service.AddressTypeServiceGetter;
import com.kalin.insurance.insurancerestapi.client.Client;
import com.kalin.insurance.insurancerestapi.client.ClientRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@SpringBootTest
class ClientServiceSaverImplTest {
    private static final Long CLIENT_ID = 1L;

    @InjectMocks
    private ClientServiceSaverImpl clientServiceSaver;
    @Mock
    private AddressTypeServiceGetter addressTypeServiceGetter;
    @Mock
    private ClientRepository clientRepository;

    private static Client client;
    private static AddressType addressType;

    @BeforeAll
    static void setup() {
        client = new Client();
        client.setId(CLIENT_ID);

        addressType = new AddressType();
        addressType.setId(1L);
        addressType.setType("home");

        Address address = new Address();
        address.setId(1L);
        address.setAddressType(addressType);

        client.setAddress(address);
    }

    @Test
    void givenClientShouldReturnAndSaveClient() {
        given(addressTypeServiceGetter.findAddressTypeByType(addressType.getType())).willReturn(addressType);
        given(clientRepository.save(client)).willReturn(client);

        assertEquals(client, clientServiceSaver.save(client));
        verify(clientRepository).save(client);
    }

    @Test
    void givenWrongAddressTypeShouldThrowAddressTypeNotFoundException() {
        given(addressTypeServiceGetter.findAddressTypeByType(addressType.getType())).willThrow(AddressTypeNotFoundException.class);
        assertThrows(AddressTypeNotFoundException.class, () -> clientServiceSaver.save(client));
    }
}