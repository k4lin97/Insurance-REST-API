package com.kalin.insurance.insurancerestapi.client.service;

import com.kalin.insurance.insurancerestapi.client.ClientRepository;
import com.kalin.insurance.insurancerestapi.client.exception.ClientNotFoundException;
import com.kalin.insurance.insurancerestapi.model.checker.ExistenceCheckerById;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doNothing;

@SpringBootTest
class ClientServiceDeleterImplTest {
    private static final Long CLIENT_ID = 1L;

    @InjectMocks
    private ClientServiceDeleterImpl clientServiceDeleter;
    @Mock
    private ClientRepository clientRepository;
    @Mock
    @Qualifier("clientExistenceCheckerById")
    private ExistenceCheckerById existenceCheckerById;

    @Test
    void givenClientIdShouldNotReturnAnythingAndDelete() {
        doNothing().when(existenceCheckerById).checkIfAlreadyExists(CLIENT_ID);
        doNothing().when(clientRepository).deleteById(CLIENT_ID);

        assertDoesNotThrow(() -> clientServiceDeleter.deleteById(CLIENT_ID));
        verify(clientRepository).deleteById(CLIENT_ID);
    }

    @Test
    void notGivenClientIdShouldThrowNotFound() {
        doThrow(ClientNotFoundException.class).when(existenceCheckerById).checkIfAlreadyExists(CLIENT_ID);
        doNothing().when(clientRepository).deleteById(CLIENT_ID);

        assertThrows(ClientNotFoundException.class, () -> clientServiceDeleter.deleteById(CLIENT_ID));
    }
}