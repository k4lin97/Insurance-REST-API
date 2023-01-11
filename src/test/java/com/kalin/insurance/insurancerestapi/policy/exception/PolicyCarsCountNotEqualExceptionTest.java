package com.kalin.insurance.insurancerestapi.policy.exception;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PolicyCarsCountNotEqualExceptionTest {
    private static final int EXISTING_CARS_COUNT = 1;
    private static final int NEW_CARS_COUNT = 3;

    @Test
    void shouldReturnValidMessage() {
        PolicyCarsCountNotEqualException policyCarsCountNotEqualException = assertThrows(PolicyCarsCountNotEqualException.class, () -> {
            throw new PolicyCarsCountNotEqualException(EXISTING_CARS_COUNT, NEW_CARS_COUNT);
        });
        assertEquals("Policy has " + EXISTING_CARS_COUNT + " cars, yet you have specified " + NEW_CARS_COUNT + " cars.", policyCarsCountNotEqualException.getMessage());
    }
}