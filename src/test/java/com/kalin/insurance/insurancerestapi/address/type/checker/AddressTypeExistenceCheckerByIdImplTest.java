package com.kalin.insurance.insurancerestapi.address.type.checker;

import com.kalin.insurance.insurancerestapi.address.type.AddressType;
import com.kalin.insurance.insurancerestapi.address.type.AddressTypeRepository;
import com.kalin.insurance.insurancerestapi.address.type.exception.AddressTypeNotFoundException;
import com.kalin.insurance.insurancerestapi.model.checker.ExistenceCheckerById;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@SpringBootTest
class AddressTypeExistenceCheckerByIdImplTest {
    private static final Long ADDRESS_TYPE_ID = 1L;

    @MockBean
    private AddressTypeRepository addressTypeRepository;
    @Autowired
    @Qualifier("addressTypeExistenceCheckerById")
    private ExistenceCheckerById existenceCheckerById;

    private static AddressType addressType;

    @BeforeAll
    static void setup() {
        addressType = new AddressType();
        addressType.setId(ADDRESS_TYPE_ID);
        addressType.setType("home");
    }

    @Test
    void givenAddressTypeFoundShouldNotThrowException() {
        given(addressTypeRepository.findById(ADDRESS_TYPE_ID)).willReturn(Optional.of(addressType));
        assertDoesNotThrow(() -> existenceCheckerById.checkIfAlreadyExists(ADDRESS_TYPE_ID));
        verify(addressTypeRepository).findById(ADDRESS_TYPE_ID);
    }

    @Test
    void givenAddressTypeNotFoundShouldThrowException() {
        given(addressTypeRepository.findById(ADDRESS_TYPE_ID)).willReturn(Optional.empty());
        assertThrows(AddressTypeNotFoundException.class, () -> existenceCheckerById.checkIfAlreadyExists(ADDRESS_TYPE_ID));
        verify(addressTypeRepository).findById(ADDRESS_TYPE_ID);
    }
}