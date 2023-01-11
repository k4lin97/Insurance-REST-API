package com.kalin.insurance.insurancerestapi.address.checker;

import com.kalin.insurance.insurancerestapi.address.Address;
import com.kalin.insurance.insurancerestapi.address.AddressRepository;
import com.kalin.insurance.insurancerestapi.address.exception.AddressNotFoundException;
import com.kalin.insurance.insurancerestapi.address.type.AddressType;
import com.kalin.insurance.insurancerestapi.model.checker.ExistenceCheckerById;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@SpringBootTest
class AddressExistenceCheckerByIdImplTest {
    private static final Long ADDRESS_TYPE_ID = 1L;
    private static final Long ADDRESS_ID = 1L;

    @Autowired
    @Qualifier("addressExistenceCheckerById")
    private ExistenceCheckerById existenceCheckerById;
    @MockBean
    private AddressRepository addressRepository;

    private static Address address;

    @BeforeAll
    static void setup() {
        AddressType addressType = new AddressType();
        addressType.setId(ADDRESS_TYPE_ID);
        addressType.setType("home");

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
    void givenAddressShouldNotThrowException() {
        given(addressRepository.findById(ADDRESS_ID)).willReturn(Optional.of(address));

        assertDoesNotThrow(() -> existenceCheckerById.checkIfAlreadyExists(ADDRESS_ID));
        verify(addressRepository).findById(ADDRESS_ID);
    }

    @Test
    void notGivenAddressShouldThrowAddressNotFoundException() {
        given(addressRepository.findById(ADDRESS_ID)).willReturn(Optional.empty());

        assertThrows(AddressNotFoundException.class, () -> existenceCheckerById.checkIfAlreadyExists(ADDRESS_ID));
        verify(addressRepository).findById(ADDRESS_ID);
    }
}