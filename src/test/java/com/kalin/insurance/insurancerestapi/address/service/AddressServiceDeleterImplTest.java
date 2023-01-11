package com.kalin.insurance.insurancerestapi.address.service;

import com.kalin.insurance.insurancerestapi.address.AddressRepository;
import com.kalin.insurance.insurancerestapi.address.exception.AddressNotFoundException;
import com.kalin.insurance.insurancerestapi.model.checker.ExistenceCheckerById;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class AddressServiceDeleterImplTest {
    private static final Long ADDRESS_ID = 1L;

    @InjectMocks
    private AddressServiceDeleterImpl addressServiceDeleter;
    @Mock
    private AddressRepository addressRepository;
    @Mock
    @Qualifier("addressExistenceCheckerById")
    private ExistenceCheckerById existenceCheckerById;

    @Test
    void givenAddressIdShouldNotReturnAnythingAndDelete() {
        doNothing().when(existenceCheckerById).checkIfAlreadyExists(ADDRESS_ID);
        doNothing().when(addressRepository).deleteById(ADDRESS_ID);

        assertDoesNotThrow(() -> addressServiceDeleter.deleteById(ADDRESS_ID));
        verify(addressRepository).deleteById(ADDRESS_ID);
    }

    @Test
    void notGivenAddressIdShouldThrowNotFound() {
        doThrow(AddressNotFoundException.class).when(existenceCheckerById).checkIfAlreadyExists(ADDRESS_ID);
        doNothing().when(addressRepository).deleteById(ADDRESS_ID);

        assertThrows(AddressNotFoundException.class, () -> addressServiceDeleter.deleteById(ADDRESS_ID));
    }
}