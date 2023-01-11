package com.kalin.insurance.insurancerestapi.client.checker;

import com.kalin.insurance.insurancerestapi.client.Client;
import com.kalin.insurance.insurancerestapi.client.ClientRepository;
import com.kalin.insurance.insurancerestapi.client.exception.ClientNotFoundException;
import com.kalin.insurance.insurancerestapi.model.checker.ExistenceCheckerById;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@SpringBootTest
class ClientExistenceCheckerByIdImplTest {
    private static final Long CLIENT_ID = 1L;

    @Autowired
    @Qualifier("clientExistenceCheckerById")
    private ExistenceCheckerById existenceCheckerById;
    @MockBean
    private ClientRepository clientRepository;

    private static Client client;

    @BeforeAll
    static void setup() {
        client = new Client();
        client.setId(CLIENT_ID);
    }

    @Test
    void givenClientShouldNotThrowException() {
        given(clientRepository.findById(CLIENT_ID)).willReturn(Optional.of(client));

        assertDoesNotThrow(() -> existenceCheckerById.checkIfAlreadyExists(CLIENT_ID));
        verify(clientRepository).findById(CLIENT_ID);
    }

    @Test
    void notGivenClientShouldThrowClientNotFountException() {
        given(clientRepository.findById(CLIENT_ID)).willReturn(Optional.empty());

        assertThrows(ClientNotFoundException.class, () -> existenceCheckerById.checkIfAlreadyExists(CLIENT_ID));
        verify(clientRepository).findById(CLIENT_ID);
    }
}