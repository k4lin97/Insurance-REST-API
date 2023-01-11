package com.kalin.insurance.insurancerestapi.address.type.service;

import com.kalin.insurance.insurancerestapi.address.type.exception.AddressTypeNotFoundException;
import com.kalin.insurance.insurancerestapi.address.type.AddressType;
import com.kalin.insurance.insurancerestapi.address.type.AddressTypeRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@SpringBootTest
class AddressTypeServiceGetterImplTest {
    private static final Long FIRST_ADDRESS_TYPE_ID = 1L;
    private static final Long SECOND_ADDRESS_TYPE_ID = 2L;

    @InjectMocks
    private AddressTypeServiceGetterImpl addressTypeServiceGetter;
    @Mock
    private AddressTypeRepository addressTypeRepository;

    private static AddressType firstAddressType;
    private static AddressType secondAddressType;
    private static List<AddressType> addressTypes;

    @BeforeAll
    static void setup() {
        firstAddressType();
        secondAddressType();
        addressTypes();
    }

    private static void firstAddressType() {
        firstAddressType = new AddressType();
        firstAddressType.setId(FIRST_ADDRESS_TYPE_ID);
        firstAddressType.setType("home");
    }

    private static void secondAddressType() {
        secondAddressType = new AddressType();
        secondAddressType.setId(SECOND_ADDRESS_TYPE_ID);
        secondAddressType.setType("work");
    }

    private static void addressTypes() {
        addressTypes = new ArrayList<>();
        addressTypes.add(firstAddressType);
        addressTypes.add(secondAddressType);
    }

    @Test
    void givenAddressTypesShouldReturnAddressTypes() {
        given(addressTypeRepository.findAll()).willReturn(addressTypes);
        assertEquals(addressTypes, addressTypeServiceGetter.findAll());
        verify(addressTypeRepository).findAll();
    }

    @Test
    void givenAddressTypeShouldReturnAddressTypeById() {
        given(addressTypeRepository.findById(FIRST_ADDRESS_TYPE_ID)).willReturn(Optional.of(firstAddressType));
        assertEquals(firstAddressType, addressTypeServiceGetter.findById(FIRST_ADDRESS_TYPE_ID));
        verify(addressTypeRepository).findById(FIRST_ADDRESS_TYPE_ID);
    }

    @Test
    void notGivenAddressTypeByIdShouldThrowNotFound() {
        given(addressTypeRepository.findById(FIRST_ADDRESS_TYPE_ID)).willReturn(Optional.empty());
        assertThrows(AddressTypeNotFoundException.class, () -> addressTypeServiceGetter.findById(FIRST_ADDRESS_TYPE_ID));
        verify(addressTypeRepository).findById(FIRST_ADDRESS_TYPE_ID);
    }

    @Test
    void givenAddressTypeShouldReturnAddressTypeByType() {
        given(addressTypeRepository.findAddressTypeByType(firstAddressType.getType())).willReturn(Optional.of(firstAddressType));
        assertEquals(firstAddressType, addressTypeServiceGetter.findAddressTypeByType(firstAddressType.getType()));
        verify(addressTypeRepository).findAddressTypeByType(firstAddressType.getType());
    }

    @Test
    void notGivenAddressTypeByTypeShouldThrowNotFound() {
        given(addressTypeRepository.findAddressTypeByType(firstAddressType.getType())).willReturn(Optional.empty());
        assertThrows(AddressTypeNotFoundException.class, () -> addressTypeServiceGetter.findAddressTypeByType(firstAddressType.getType()));
        verify(addressTypeRepository).findAddressTypeByType(firstAddressType.getType());
    }
}