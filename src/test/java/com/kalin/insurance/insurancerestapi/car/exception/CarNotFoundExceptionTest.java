package com.kalin.insurance.insurancerestapi.car.exception;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CarNotFoundExceptionTest {
    private static final Long ID = 1L;

    @Test
    void shouldReturnValidMessage() {
        CarNotFoundException carNotFoundException = assertThrows(CarNotFoundException.class, () -> {
            throw new CarNotFoundException(ID);
        });
        assertEquals("Car with id: " + ID + " was not found", carNotFoundException.getMessage());
    }
}