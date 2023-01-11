package com.kalin.insurance.insurancerestapi.address.type.service;

import com.kalin.insurance.insurancerestapi.address.type.exception.AddressTypeDuplicatedException;
import com.kalin.insurance.insurancerestapi.address.type.exception.AddressTypeNotFoundException;
import com.kalin.insurance.insurancerestapi.address.type.AddressType;
import com.kalin.insurance.insurancerestapi.address.type.AddressTypeRepository;
import com.kalin.insurance.insurancerestapi.address.type.checker.AddressTypeExistenceCheckerByAddressType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest
class AddressTypeServiceSaverImplTest {
    private static final Long ADDRESS_TYPE_ID = 1L;

    @InjectMocks
    private AddressTypeServiceSaverImpl addressTypeServiceSaver;
    @Mock
    private AddressTypeRepository addressTypeRepository;
    @Mock
    private AddressTypeExistenceCheckerByAddressType addressTypeExistenceCheckerByAddressType;

    private static AddressType addressType;

    @BeforeAll
    static void setup() {
        addressType();
    }

    private static void addressType() {
        addressType = new AddressType();
        addressType.setId(ADDRESS_TYPE_ID);
        addressType.setType("home");
    }

    @Test
    void givenAddressTypeShouldReturnAddressType() {
        doNothing().when(addressTypeExistenceCheckerByAddressType).checkIfAddressTypeAlreadyExists(addressType);
        given(addressTypeRepository.save(addressType)).willReturn(addressType);

        assertEquals(addressType, addressTypeServiceSaver.save(addressType));
        verify(addressTypeRepository).save(addressType);
    }

    @Test
    void notGivenAddressTypeShouldThrowAddressTypeNotFoundException() {
        AddressType addressTypeNull = null;
        doThrow(AddressTypeNotFoundException.class).when(addressTypeExistenceCheckerByAddressType).checkIfAddressTypeAlreadyExists(addressTypeNull);
        assertThrows(AddressTypeNotFoundException.class, () -> addressTypeServiceSaver.save(addressTypeNull));
    }

    @Test
    void givenDuplicatedAddressTypeShouldThrowAddressTypeDuplicatedException() {
        doThrow(AddressTypeDuplicatedException.class).when(addressTypeExistenceCheckerByAddressType).checkIfAddressTypeAlreadyExists(addressType);
        assertThrows(AddressTypeDuplicatedException.class, () -> addressTypeServiceSaver.save(addressType));
    }
}