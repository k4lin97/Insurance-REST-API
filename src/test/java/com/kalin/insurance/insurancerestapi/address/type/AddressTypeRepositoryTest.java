package com.kalin.insurance.insurancerestapi.address.type;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Sql("classpath:address-types.sql")
class AddressTypeRepositoryTest {
    private static final String ZERO_COUNT_ADDRESS_TYPE = "test";
    private static final String ONE_COUNT_ADDRESS_TYPE = "home";
    private static final String TWO_COUNT_ADDRESS_TYPE = "work";

    @Autowired
    private AddressTypeRepository addressTypeRepository;

    @Test
    void shouldReturnZero() {
        assertEquals(0, addressTypeRepository.countAddressTypesByType(ZERO_COUNT_ADDRESS_TYPE));
    }

    @Test
    void shouldReturnOne() {
        assertEquals(1, addressTypeRepository.countAddressTypesByType(ONE_COUNT_ADDRESS_TYPE));
    }

    @Test
    void shouldReturnTwo() {
        assertEquals(2, addressTypeRepository.countAddressTypesByType(TWO_COUNT_ADDRESS_TYPE));
    }
}