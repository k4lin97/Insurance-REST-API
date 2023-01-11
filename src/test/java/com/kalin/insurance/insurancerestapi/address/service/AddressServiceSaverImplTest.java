package com.kalin.insurance.insurancerestapi.address.service;

import com.kalin.insurance.insurancerestapi.address.Address;
import com.kalin.insurance.insurancerestapi.address.AddressRepository;
import com.kalin.insurance.insurancerestapi.address.type.exception.AddressTypeNotFoundException;
import com.kalin.insurance.insurancerestapi.address.type.AddressType;
import com.kalin.insurance.insurancerestapi.address.type.service.AddressTypeServiceGetter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@SpringBootTest
class AddressServiceSaverImplTest {
    private static final Long ADDRESS_ID = 1L;
    private static final Long ADDRESS_TYPE_ID = 1L;

    @InjectMocks
    private AddressServiceSaverImpl addressServiceSaver;
    @Mock
    private AddressTypeServiceGetter addressTypeServiceGetter;
    @Mock
    private AddressRepository addressRepository;

    private static AddressType addressType;
    private static Address address;

    @BeforeAll
    static void setup() {
        addressType();
        address();
    }

    private static void addressType() {
        addressType = new AddressType();
        addressType.setId(ADDRESS_TYPE_ID);
        addressType.setType("home");
    }

    private static void address() {
        address = new Address();
        address.setId(ADDRESS_ID);
        address.setAddressType(addressType);
        address.setCity("Gdansk");
        address.setPostCode("12-593");
        address.setCountry("Poland");
        address.setStreet("Zielona");
        address.setStreetNumber("2");
    }

    @Test
    void givenAddressShouldReturnAndSaveAddress() {
        given(addressTypeServiceGetter.findAddressTypeByType(address.getAddressType().getType())).willReturn(addressType);
        given(addressRepository.save(address)).willReturn(address);

        assertEquals(address, addressServiceSaver.save(address));
        verify(addressRepository).save(address);
    }

    @Test
    void notGivenAddressTypeShouldThrowAddressTypeNotFoundException() {
        doThrow(AddressTypeNotFoundException.class).when(addressTypeServiceGetter).findAddressTypeByType(address.getAddressType().getType());
        assertThrows(AddressTypeNotFoundException.class, () -> addressServiceSaver.save(address));
    }
}