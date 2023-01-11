package com.kalin.insurance.insurancerestapi.address.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kalin.insurance.insurancerestapi.address.Address;
import com.kalin.insurance.insurancerestapi.address.exception.AddressNotFoundException;
import com.kalin.insurance.insurancerestapi.address.service.AddressServiceGetter;
import com.kalin.insurance.insurancerestapi.address.type.AddressType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(AddressControllerGetter.class)
@WithMockUser
class AddressControllerGetterTest {
    private static final Long ADDRESS_TYPE_ID = 1L;
    private static final Long FIRST_ADDRESS_ID = 1L;
    private static final Long SECOND_ADDRESS_ID = 2L;
    private static final String ADDRESS_URL = "/addresses/";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private AddressServiceGetter addressServiceGetter;

    private static AddressType addressType;
    private static Address address;
    private static List<Address> addresses;

    @BeforeAll
    static void setup() {
        addressType();
        address();
        addresses();
    }

    private static void addressType() {
        addressType = new AddressType();
        addressType.setId(ADDRESS_TYPE_ID);
        addressType.setType("home");
    }

    private static void address() {
        address = new Address();
        address.setId(FIRST_ADDRESS_ID);
        address.setAddressType(addressType);
        address.setCity("Gdansk");
        address.setPostCode("12-593");
        address.setCountry("Poland");
        address.setStreet("Zielona");
        address.setStreetNumber("2");
    }

    private static void addresses() {
        addresses = new ArrayList<>();
        Address secondAddress = new Address();
        secondAddress.setId(SECOND_ADDRESS_ID);
        secondAddress.setAddressType(addressType);
        secondAddress.setCity("Gdynia");
        secondAddress.setPostCode("87-568");
        secondAddress.setCountry("Poland");
        secondAddress.setStreet("Czerwona");
        secondAddress.setStreetNumber("12a");
        addresses.add(address);
        addresses.add(secondAddress);
    }

    @Test
    void givenAddressesShouldReturnAddresses() throws Exception {
        when(addressServiceGetter.findAll()).thenReturn(addresses);

        StringWriter stringWriter = new StringWriter();
        objectMapper.writeValue(stringWriter, addresses);
        final String expectedJson = stringWriter.toString();

        mockMvc.perform(get(ADDRESS_URL))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));

        verify(addressServiceGetter).findAll();
    }

    @Test
    void givenAddressShouldReturnAddressById() throws Exception {
        when(addressServiceGetter.findById(FIRST_ADDRESS_ID)).thenReturn(address);

        final String expectedJson = objectMapper.writeValueAsString(address);

        mockMvc.perform(get(ADDRESS_URL + FIRST_ADDRESS_ID))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
        verify(addressServiceGetter).findById(FIRST_ADDRESS_ID);
    }

    @Test
    void notGivenAddressShouldReturnNotFound() throws Exception {
        when(addressServiceGetter.findById(FIRST_ADDRESS_ID)).thenThrow(AddressNotFoundException.class);

        mockMvc.perform(get(ADDRESS_URL + FIRST_ADDRESS_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.status").value(String.valueOf(HttpStatus.NOT_FOUND.value())))
                .andExpect(jsonPath("$.error").isNotEmpty())
                .andExpect(jsonPath("$.messages").isNotEmpty());
        verify(addressServiceGetter).findById(FIRST_ADDRESS_ID);
    }
}