package com.kalin.insurance.insurancerestapi.address.type.exception;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AddressTypeDuplicatedExceptionTest {
    private static final String MESSAGE = "test message";

    @Test
    void shouldReturnValidMessage() {
        AddressTypeDuplicatedException addressTypeDuplicatedException = assertThrows(AddressTypeDuplicatedException.class, () -> {
            throw new AddressTypeDuplicatedException(MESSAGE);
        });
        assertEquals("Address type: " + MESSAGE + " already exist.", addressTypeDuplicatedException.getMessage());
    }
}