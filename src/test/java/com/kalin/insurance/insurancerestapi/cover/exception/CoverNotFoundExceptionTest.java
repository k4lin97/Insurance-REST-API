package com.kalin.insurance.insurancerestapi.cover.exception;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CoverNotFoundExceptionTest {
    private static final Long ID = 1L;

    @Test
    void shouldReturnValidMessage() {
        CoverNotFoundException coverNotFoundException = assertThrows(CoverNotFoundException.class, () -> {
            throw new CoverNotFoundException(ID);
        });
        assertEquals("Cover with id: " + ID + " was not found.", coverNotFoundException.getMessage());
    }
}