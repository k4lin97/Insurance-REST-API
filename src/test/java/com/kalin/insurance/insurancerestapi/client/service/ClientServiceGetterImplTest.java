package com.kalin.insurance.insurancerestapi.client.service;

import com.kalin.insurance.insurancerestapi.client.Client;
import com.kalin.insurance.insurancerestapi.client.ClientRepository;
import com.kalin.insurance.insurancerestapi.client.exception.ClientNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@SpringBootTest
class ClientServiceGetterImplTest {
    private static final Long FIRST_CLIENT_ID = 1L;
    private static final Long SECOND_CLIENT_ID = 2L;

    @InjectMocks
    private ClientServiceGetterImpl clientServiceGetter;
    @Mock
    private ClientRepository clientRepository;

    private static Client client;
    private static List<Client> clients;

    @BeforeAll
    static void setup() {
        client = new Client();
        client.setId(FIRST_CLIENT_ID);

        Client secondClient = new Client();
        secondClient.setId(SECOND_CLIENT_ID);

        clients = new ArrayList<>();
        clients.add(client);
        clients.add(secondClient);
    }

    @Test
    void givenClientsShouldReturnClients() {
        given(clientRepository.findAll()).willReturn(clients);
        assertEquals(clients, clientServiceGetter.findAll());
        verify(clientRepository).findAll();
    }

    @Test
    void givenClientIdShouldReturnClientById() {
        given(clientRepository.findById(FIRST_CLIENT_ID)).willReturn(Optional.of(client));
        assertEquals(client, clientServiceGetter.findById(FIRST_CLIENT_ID));
        verify(clientRepository).findById(FIRST_CLIENT_ID);
    }

    @Test
    void notGivenClientIdShouldThrowNotFound() {
        given(clientRepository.findById(FIRST_CLIENT_ID)).willReturn(Optional.empty());
        assertThrows(ClientNotFoundException.class, () -> clientServiceGetter.findById(FIRST_CLIENT_ID));
        verify(clientRepository).findById(FIRST_CLIENT_ID);
    }
}