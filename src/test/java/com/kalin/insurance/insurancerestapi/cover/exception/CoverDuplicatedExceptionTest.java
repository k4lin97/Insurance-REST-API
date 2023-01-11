package com.kalin.insurance.insurancerestapi.cover.exception;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CoverDuplicatedExceptionTest {
    private static final Long ID = 1L;
    private static final String MESSAGE = "test message";

    @Test
    void shouldReturnValidMessage() {
        CoverDuplicatedException coverDuplicatedException = assertThrows(CoverDuplicatedException.class, () -> {
            throw new CoverDuplicatedException(MESSAGE, ID);
        });
        assertEquals("Cover: " + MESSAGE + " is already added to the car of id: " + ID + ".", coverDuplicatedException.getMessage());
    }
}