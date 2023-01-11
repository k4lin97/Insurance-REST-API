package com.kalin.insurance.insurancerestapi.address.exception;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AddressNotFoundExceptionTest {
    private static final Long ID = 1L;

    @Test
    void shouldReturnValidMessage() {
        AddressNotFoundException addressNotFoundException = assertThrows(AddressNotFoundException.class, () -> {
            throw new AddressNotFoundException(ID);
        });
        assertEquals("Address with id: " + ID + " was not found", addressNotFoundException.getMessage());
    }
}