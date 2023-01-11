package com.kalin.insurance.insurancerestapi.address.type;

import com.google.common.testing.EqualsTester;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AddressTypeTest {
    private static AddressType addressType;
    private static AddressType sameAddressType;
    private static AddressType differentAddressType;

    @BeforeAll
    static void setup() {
        addressType = new AddressType();
        addressType.setId(1L);
        addressType.setType("home");

        sameAddressType = new AddressType();
        sameAddressType.setId(2L);
        sameAddressType.setType("home");

        differentAddressType = new AddressType();
        differentAddressType.setId(3L);
        differentAddressType.setType("work");
    }

    @Test
    void testEqualsWithGuava() {
        new EqualsTester()
                .addEqualityGroup(addressType, sameAddressType)
                .addEqualityGroup(differentAddressType)
                .testEquals();
    }
}