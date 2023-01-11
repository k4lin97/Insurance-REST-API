package com.kalin.insurance.insurancerestapi.address.type.checker;

import com.kalin.insurance.insurancerestapi.address.type.exception.AddressTypeDuplicatedException;
import com.kalin.insurance.insurancerestapi.address.type.AddressType;
import com.kalin.insurance.insurancerestapi.address.type.AddressTypeRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@SpringBootTest
class AddressTypeExistenceCheckerByAddressTypeImplTest {
    private static final Long ADDRESS_TYPE_ID = 1L;

    @MockBean
    private AddressTypeRepository addressTypeRepository;
    @Autowired
    private AddressTypeExistenceCheckerByAddressTypeImpl addressTypeExistenceChecker;

    private static AddressType addressType;

    @BeforeAll
    static void setup() {
        addressType = new AddressType();
        addressType.setId(ADDRESS_TYPE_ID);
        addressType.setType("home");
    }

    @Test
    void givenAddressTypeAlreadyExistingShouldThrowException() {
        given(addressTypeRepository.countAddressTypesByType(addressType.getType())).willReturn(1);
        assertThrows(AddressTypeDuplicatedException.class, () -> addressTypeExistenceChecker.checkIfAddressTypeAlreadyExists(addressType));
        verify(addressTypeRepository).countAddressTypesByType(addressType.getType());
    }

    @Test
    void givenAddressTypeNotExistingShouldNotThrowException() {
        given(addressTypeRepository.countAddressTypesByType(addressType.getType())).willReturn(0);
        assertDoesNotThrow(() -> addressTypeExistenceChecker.checkIfAddressTypeAlreadyExists(addressType));
        verify(addressTypeRepository).countAddressTypesByType(addressType.getType());
    }
}