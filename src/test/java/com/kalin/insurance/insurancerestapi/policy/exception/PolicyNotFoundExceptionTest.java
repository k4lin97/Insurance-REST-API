package com.kalin.insurance.insurancerestapi.policy.exception;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PolicyNotFoundExceptionTest {
    private static final Long ID = 1L;

    @Test
    void shouldReturnValidMessage() {
        PolicyNotFoundException policyNotFoundException = assertThrows(PolicyNotFoundException.class, () -> {
            throw new PolicyNotFoundException(ID);
        });
        assertEquals("Policy with id: " + ID + " was not found.", policyNotFoundException.getMessage());
    }
}