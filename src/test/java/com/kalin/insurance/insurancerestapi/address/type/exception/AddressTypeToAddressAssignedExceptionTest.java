package com.kalin.insurance.insurancerestapi.address.type.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressTypeToAddressAssignedExceptionTest {
    private static final Long ID = 1L;

    @Test
    void shouldReturnValidMessage() {
        AddressTypeToAddressAssignedException addressTypeToAddressAssignedException = assertThrows(AddressTypeToAddressAssignedException.class, () -> {
            throw new AddressTypeToAddressAssignedException(ID);
        });
        assertEquals("Address type with id: " + ID + " is assigned to some address.", addressTypeToAddressAssignedException.getMessage());
    }
}