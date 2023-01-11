package com.kalin.insurance.insurancerestapi.address.type.service;

import com.kalin.insurance.insurancerestapi.address.type.exception.AddressTypeNotFoundException;
import com.kalin.insurance.insurancerestapi.address.type.exception.AddressTypeToAddressAssignedException;
import com.kalin.insurance.insurancerestapi.address.type.AddressTypeRepository;
import com.kalin.insurance.insurancerestapi.address.type.checker.AddressAssignmentChecker;
import com.kalin.insurance.insurancerestapi.model.checker.ExistenceCheckerById;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class AddressTypeServiceDeleterImplTest {
    private static final Long ADDRESS_TYPE_ID = 1L;

    @InjectMocks
    private AddressTypeServiceDeleterImpl addressTypeServiceDeleter;
    @Mock
    private AddressTypeRepository addressTypeRepository;
    @Mock
    @Qualifier("addressTypeExistenceCheckerById")
    private ExistenceCheckerById existenceCheckerById;
    @Mock
    private AddressAssignmentChecker addressAssignmentChecker;

    @Test
    void notGivenAddressTypeShouldThrowAddressTypeNotFoundException() {
        doThrow(AddressTypeNotFoundException.class).when(existenceCheckerById).checkIfAlreadyExists(ADDRESS_TYPE_ID);
        doNothing().when(addressAssignmentChecker).checkIfAddressTypeIsAssignedToAddress(ADDRESS_TYPE_ID);
        doNothing().when(addressTypeRepository).deleteById(ADDRESS_TYPE_ID);

        assertThrows(AddressTypeNotFoundException.class, () -> addressTypeServiceDeleter.deleteById(ADDRESS_TYPE_ID));
    }

    @Test
    void givenAddressTypeToAddressAssignedShouldThrowAddressTypeToAddressAssignedException() {
        doNothing().when(existenceCheckerById).checkIfAlreadyExists(ADDRESS_TYPE_ID);
        doThrow(AddressTypeToAddressAssignedException.class).when(addressAssignmentChecker).checkIfAddressTypeIsAssignedToAddress(ADDRESS_TYPE_ID);
        doNothing().when(addressTypeRepository).deleteById(ADDRESS_TYPE_ID);

        assertThrows(AddressTypeToAddressAssignedException.class, () -> addressTypeServiceDeleter.deleteById(ADDRESS_TYPE_ID));
    }

    @Test
    void givenAddressTypeShouldNotReturnAnythingAndDelete() {
        doNothing().when(existenceCheckerById).checkIfAlreadyExists(ADDRESS_TYPE_ID);
        doNothing().when(addressAssignmentChecker).checkIfAddressTypeIsAssignedToAddress(ADDRESS_TYPE_ID);
        doNothing().when(addressTypeRepository).deleteById(ADDRESS_TYPE_ID);

        assertDoesNotThrow(() -> addressTypeServiceDeleter.deleteById(ADDRESS_TYPE_ID));
        verify(addressTypeRepository).deleteById(ADDRESS_TYPE_ID);
    }
}