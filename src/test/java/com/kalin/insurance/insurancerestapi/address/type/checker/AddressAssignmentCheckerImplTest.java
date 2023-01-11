package com.kalin.insurance.insurancerestapi.address.type.checker;

import com.kalin.insurance.insurancerestapi.address.AddressRepository;
import com.kalin.insurance.insurancerestapi.address.type.exception.AddressTypeToAddressAssignedException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@SpringBootTest
class AddressAssignmentCheckerImplTest {
    private static final Long ADDRESS_TYPE_ID = 1L;

    @MockBean
    private AddressRepository addressRepository;
    @Autowired
    private AddressAssignmentChecker addressAssignmentChecker;

    @Test
    void zeroShouldNotThrowException() {
        given(addressRepository.countAddressesByAddressTypeId(ADDRESS_TYPE_ID)).willReturn(0);
        assertDoesNotThrow(() -> addressAssignmentChecker.checkIfAddressTypeIsAssignedToAddress(ADDRESS_TYPE_ID));
        verify(addressRepository).countAddressesByAddressTypeId(ADDRESS_TYPE_ID);
    }

    @Test
    void negativeOneShouldNotThrowException() {
        given(addressRepository.countAddressesByAddressTypeId(ADDRESS_TYPE_ID)).willReturn(-1);
        assertDoesNotThrow(() -> addressAssignmentChecker.checkIfAddressTypeIsAssignedToAddress(ADDRESS_TYPE_ID));
        verify(addressRepository).countAddressesByAddressTypeId(ADDRESS_TYPE_ID);
    }

    @Test
    void oneShouldThrowException() {
        given(addressRepository.countAddressesByAddressTypeId(ADDRESS_TYPE_ID)).willReturn(1);
        assertThrows(AddressTypeToAddressAssignedException.class, () -> addressAssignmentChecker.checkIfAddressTypeIsAssignedToAddress(ADDRESS_TYPE_ID));
        verify(addressRepository).countAddressesByAddressTypeId(ADDRESS_TYPE_ID);
    }
}