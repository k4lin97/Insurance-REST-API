package com.kalin.insurance.insurancerestapi.address;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Sql("classpath:addresses.sql")
class AddressRepositoryTest {
    private static final Long ZERO_COUNT_ADDRESS_TYPE_ID = 3L;
    private static final Long ONE_COUNT_ADDRESS_TYPE_ID = 2L;
    private static final Long TWO_COUNT_ADDRESS_TYPE_ID = 1L;

    @Autowired
    private AddressRepository addressRepository;

    @Test
    void shouldReturnZero() {
        assertEquals(0, addressRepository.countAddressesByAddressTypeId(ZERO_COUNT_ADDRESS_TYPE_ID));
    }

    @Test
    void shouldReturnOne() {
        assertEquals(1, addressRepository.countAddressesByAddressTypeId(ONE_COUNT_ADDRESS_TYPE_ID));
    }

    @Test
    void shouldReturnTwo() {
        assertEquals(2, addressRepository.countAddressesByAddressTypeId(TWO_COUNT_ADDRESS_TYPE_ID));
    }
}