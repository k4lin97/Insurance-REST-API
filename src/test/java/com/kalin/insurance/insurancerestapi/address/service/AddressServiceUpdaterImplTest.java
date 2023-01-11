package com.kalin.insurance.insurancerestapi.address.service;

import com.kalin.insurance.insurancerestapi.address.Address;
import com.kalin.insurance.insurancerestapi.address.exception.AddressNotFoundException;
import com.kalin.insurance.insurancerestapi.address.type.AddressType;
import com.kalin.insurance.insurancerestapi.model.checker.ExistenceCheckerById;
import com.kalin.insurance.insurancerestapi.model.service.ServiceSaver;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest
class AddressServiceUpdaterImplTest {
    private static final Long ADDRESS_ID = 1L;
    private static final Long ADDRESS_TYPE_ID = 1L;

    @InjectMocks
    private AddressServiceUpdaterImpl addressServiceUpdater;
    @Mock
    private ServiceSaver<Address> addressServiceSaver;
    @Mock
    @Qualifier("addressExistenceCheckerById")
    private ExistenceCheckerById existenceCheckerById;

    private static AddressType addressType;
    private static Address address;

    @BeforeAll
    static void setup() {
        updatedAddressType();
        updatedAddress();
    }

    private static void updatedAddressType() {
        addressType = new AddressType();
        addressType.setId(ADDRESS_TYPE_ID);
        addressType.setType("work");
    }

    private static void updatedAddress() {
        address = new Address();
        address.setId(ADDRESS_ID);
        address.setAddressType(addressType);
        address.setCity("Gdynia");
        address.setPostCode("96-563");
        address.setCountry("Poland");
        address.setStreet("Kwiecista");
        address.setStreetNumber("12a");
    }

    @Test
    void givenExistingIdShouldReturnUpdatedAddress() {
        doNothing().when(existenceCheckerById).checkIfAlreadyExists(ADDRESS_ID);
        given(addressServiceSaver.save(address)).willReturn(address);

        assertEquals(address, addressServiceUpdater.update(address, ADDRESS_ID));
        verify(addressServiceSaver).save(address);
    }

    @Test
    void givenNotExistingIdShouldThrowAddressNotFoundException() {
        doThrow(AddressNotFoundException.class).when(existenceCheckerById).checkIfAlreadyExists(ADDRESS_ID);
        assertThrows(AddressNotFoundException.class, () -> addressServiceUpdater.update(address, ADDRESS_ID));
    }
}