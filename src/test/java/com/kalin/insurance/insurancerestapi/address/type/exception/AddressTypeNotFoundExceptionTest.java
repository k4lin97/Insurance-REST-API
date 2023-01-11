package com.kalin.insurance.insurancerestapi.address.type.exception;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AddressTypeNotFoundExceptionTest {
    private static final String MESSAGE = "test message";
    private static final Long ID = 1L;

    @Test
    void shouldReturnValidMessageForStringInput() {
        AddressTypeNotFoundException addressTypeNotFoundException = assertThrows(AddressTypeNotFoundException.class, () -> {
            throw new AddressTypeNotFoundException(MESSAGE);
        });
        assertEquals("Address type: " + MESSAGE + " was not found.", addressTypeNotFoundException.getMessage());
    }

    @Test
    void shouldReturnValidMessageForLongInput() {
        AddressTypeNotFoundException addressTypeNotFoundException = assertThrows(AddressTypeNotFoundException.class, () -> {
            throw new AddressTypeNotFoundException(ID);
        });
        assertEquals("Address type with id: " + ID + " was not found.", addressTypeNotFoundException.getMessage());
    }
}