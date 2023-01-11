package com.kalin.insurance.insurancerestapi.policy.exception;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PolicyNotConsistentExceptionTest {
    private static List<String> errors;

    @BeforeAll
    static void setup() {
        errors = new ArrayList<>();
        errors.add("Test message 0");
        errors.add("Test message 1");
        errors.add("Test message 2");
    }

    @Test
    void shouldReturnValidMessage() {
        PolicyNotConsistentException policyNotConsistentException = assertThrows(PolicyNotConsistentException.class, () -> {
            throw new PolicyNotConsistentException(errors);
        });
        assertEquals(errors, policyNotConsistentException.getErrors());
    }
}