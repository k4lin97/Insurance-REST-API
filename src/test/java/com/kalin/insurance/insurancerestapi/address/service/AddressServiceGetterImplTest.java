package com.kalin.insurance.insurancerestapi.address.service;

import com.kalin.insurance.insurancerestapi.address.Address;
import com.kalin.insurance.insurancerestapi.address.AddressRepository;
import com.kalin.insurance.insurancerestapi.address.exception.AddressNotFoundException;
import com.kalin.insurance.insurancerestapi.address.type.AddressType;
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
class AddressServiceGetterImplTest {
    private static final Long FIRST_ADDRESS_ID = 1L;
    private static final Long SECOND_ADDRESS_ID = 2L;
    private static final Long ADDRESS_TYPE_ID = 1L;

    @InjectMocks
    private AddressServiceGetterImpl addressServiceGetter;
    @Mock
    private AddressRepository addressRepository;

    private static AddressType addressType;
    private static Address firstAddress;
    private static Address secondAddress;
    private static List<Address> addresses;

    @BeforeAll
    static void setup() {
        addressType();
        firstAddress();
        secondAddress();
        addresses();
    }

    private static void addressType() {
        addressType = new AddressType();
        addressType.setId(ADDRESS_TYPE_ID);
        addressType.setType("home");
    }

    private static void firstAddress() {
        firstAddress = new Address();
        firstAddress.setId(FIRST_ADDRESS_ID);
        firstAddress.setAddressType(addressType);
        firstAddress.setCity("Gdansk");
        firstAddress.setPostCode("12-593");
        firstAddress.setCountry("Poland");
        firstAddress.setStreet("Zielona");
        firstAddress.setStreetNumber("2");
    }

    private static void secondAddress() {
        secondAddress = new Address();
        secondAddress.setId(SECOND_ADDRESS_ID);
        secondAddress.setAddressType(addressType);
        secondAddress.setCity("Gdynia");
        secondAddress.setPostCode("87-568");
        secondAddress.setCountry("Poland");
        secondAddress.setStreet("Czerwona");
        secondAddress.setStreetNumber("12a");
    }

    private static void addresses() {
        addresses = new ArrayList<>();
        addresses.add(firstAddress);
        addresses.add(secondAddress);
    }

    @Test
    void givenAddressesShouldReturnAddresses() {
        given(addressRepository.findAll()).willReturn(addresses);
        assertEquals(addresses, addressServiceGetter.findAll());
        verify(addressRepository).findAll();
    }

    @Test
    void givenAddressIdShouldReturnAddressById() {
        given(addressRepository.findById(FIRST_ADDRESS_ID)).willReturn(Optional.of(firstAddress));
        assertEquals(firstAddress, addressServiceGetter.findById(FIRST_ADDRESS_ID));
        verify(addressRepository).findById(FIRST_ADDRESS_ID);
    }

    @Test
    void notGivenAddressIdShouldThrowNotFound() {
        given(addressRepository.findById(FIRST_ADDRESS_ID)).willReturn(Optional.empty());
        assertThrows(AddressNotFoundException.class, () -> addressServiceGetter.findById(FIRST_ADDRESS_ID));
        verify(addressRepository).findById(FIRST_ADDRESS_ID);
    }
}