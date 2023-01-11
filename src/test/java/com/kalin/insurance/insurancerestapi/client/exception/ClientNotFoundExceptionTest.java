package com.kalin.insurance.insurancerestapi.client.exception;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ClientNotFoundExceptionTest {
    private static final Long ID = 1L;

    @Test
    void shouldReturnValidMessage() {
        ClientNotFoundException clientNotFoundException = assertThrows(ClientNotFoundException.class, () -> {
            throw new ClientNotFoundException(ID);
        });
        assertEquals("Client with id: " + ID + " was not found.", clientNotFoundException.getMessage());
    }
}