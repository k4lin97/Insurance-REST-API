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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest
class AddressTypeServiceUpdaterImplTest {
    private static final Long ADDRESS_TYPE_ID = 1L;

    @InjectMocks
    private AddressTypeServiceUpdaterImpl addressTypeServiceUpdater;
    @Mock
    private AddressTypeRepository addressTypeRepository;
    @Mock
    private AddressTypeExistenceCheckerByAddressType addressTypeExistenceCheckerByAddressType;


    private static AddressType addressType;
    private static AddressType updatedAddressType;
    private static AddressType updatedAddressTypeSameType;

    @BeforeAll
    static void setup() {
        addressType();
        updatedAddressType();
        updatedAddressTypeSameType();
    }

    private static void addressType() {
        addressType = new AddressType();
        addressType.setId(ADDRESS_TYPE_ID);
        addressType.setType("home");
    }

    private static void updatedAddressType() {
        updatedAddressType = new AddressType();
        updatedAddressType.setId(ADDRESS_TYPE_ID);
        updatedAddressType.setType("work");
    }

    private static void updatedAddressTypeSameType() {
        updatedAddressTypeSameType = new AddressType();
        updatedAddressTypeSameType.setId(ADDRESS_TYPE_ID);
        updatedAddressTypeSameType.setType("home");
    }

    @Test
    void givenUpdatedAddressTypeWithDifferentTypeShouldUpdateAddressType() {
        given(addressTypeRepository.findById(ADDRESS_TYPE_ID)).willReturn(Optional.of(addressType));
        doNothing().when(addressTypeExistenceCheckerByAddressType).checkIfAddressTypeAlreadyExists(addressType);
        given(addressTypeRepository.save(updatedAddressType)).willReturn(updatedAddressType);

        assertEquals(updatedAddressType, addressTypeServiceUpdater.update(updatedAddressType, addressType.getId()));
        verify(addressTypeRepository).save(updatedAddressType);
    }

    @Test
    void givenUpdatedAddressTypeWithSameTypeShouldUpdateAddressType() {
        given(addressTypeRepository.findById(ADDRESS_TYPE_ID)).willReturn(Optional.of(addressType));
        given(addressTypeRepository.save(updatedAddressTypeSameType)).willReturn(updatedAddressTypeSameType);

        assertEquals(updatedAddressTypeSameType, addressTypeServiceUpdater.update(updatedAddressTypeSameType, addressType.getId()));
        verify(addressTypeRepository).save(updatedAddressTypeSameType);
    }

    @Test
    void givenNotFoundIdShouldThrowAddressTypeNotFoundException() {
        given(addressTypeRepository.findById(ADDRESS_TYPE_ID)).willReturn(Optional.empty());
        assertThrows(AddressTypeNotFoundException.class, () -> addressTypeServiceUpdater.update(updatedAddressType, addressType.getId()));
    }

    @Test
    void givenExistingAddressTypeShouldThrowAddressTypeDuplicatedException() {
        given(addressTypeRepository.findById(ADDRESS_TYPE_ID)).willReturn(Optional.of(addressType));
        doThrow(AddressTypeDuplicatedException.class).when(addressTypeExistenceCheckerByAddressType).checkIfAddressTypeAlreadyExists(updatedAddressType);

        assertThrows(AddressTypeDuplicatedException.class, () -> addressTypeServiceUpdater.update(updatedAddressType, addressType.getId()));
    }

    @Test
    void givenNullAddressTypeShouldThrowAddressTypeNotFoundException() {
        given(addressTypeRepository.findById(ADDRESS_TYPE_ID)).willReturn(Optional.empty());
        doThrow(AddressTypeNotFoundException.class).when(addressTypeExistenceCheckerByAddressType).checkIfAddressTypeAlreadyExists(null);

        assertThrows(AddressTypeNotFoundException.class, () -> addressTypeServiceUpdater.update(null, addressType.getId()));
    }
}