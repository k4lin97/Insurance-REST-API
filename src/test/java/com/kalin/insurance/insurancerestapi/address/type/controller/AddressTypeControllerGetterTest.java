package com.kalin.insurance.insurancerestapi.address.type.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kalin.insurance.insurancerestapi.address.type.exception.AddressTypeNotFoundException;
import com.kalin.insurance.insurancerestapi.address.type.AddressType;
import com.kalin.insurance.insurancerestapi.address.type.service.AddressTypeServiceGetter;
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

@WebMvcTest(AddressTypeControllerGetter.class)
@WithMockUser
class AddressTypeControllerGetterTest {
    private static final Long FIRST_ADDRESS_TYPE_ID = 1L;
    private static final Long SECOND_ADDRESS_TYPE_ID = 1L;
    private static final String ADDRESS_TYPES_URL = "/addresses/types/";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private AddressTypeServiceGetter addressTypeServiceGetter;

    private static AddressType addressType;
    private static List<AddressType> addressTypes;

    @BeforeAll
    static void setup() {
        addressType();
        addressTypes();
    }

    private static void addressType() {
        addressType = new AddressType();
        addressType.setId(FIRST_ADDRESS_TYPE_ID);
        addressType.setType("home");
    }

    private static void addressTypes() {
        addressTypes = new ArrayList<>();
        AddressType addressType1 = new AddressType();
        addressType1.setId(SECOND_ADDRESS_TYPE_ID);
        addressType1.setType("work");
        addressTypes.add(addressType);
        addressTypes.add(addressType1);
    }

    @Test
    void givenAddressTypesShouldReturnAddressTypes() throws Exception {
        when(addressTypeServiceGetter.findAll()).thenReturn(addressTypes);

        StringWriter stringWriter = new StringWriter();
        objectMapper.writeValue(stringWriter, addressTypes);
        final String expectedJson = stringWriter.toString();

        mockMvc.perform(get(ADDRESS_TYPES_URL))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));

        verify(addressTypeServiceGetter).findAll();
    }

    @Test
    void givenAddressTypeShouldReturnAddressTypeById() throws Exception {
        when(addressTypeServiceGetter.findById(FIRST_ADDRESS_TYPE_ID)).thenReturn(addressType);

        final String expectedJson = objectMapper.writeValueAsString(addressType);

        mockMvc.perform(get(ADDRESS_TYPES_URL + FIRST_ADDRESS_TYPE_ID))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
        verify(addressTypeServiceGetter).findById(FIRST_ADDRESS_TYPE_ID);
    }

    @Test
    void notGivenAddressTypeShouldReturnNotFound() throws Exception {
        when(addressTypeServiceGetter.findById(FIRST_ADDRESS_TYPE_ID)).thenThrow(AddressTypeNotFoundException.class);

        mockMvc.perform(get(ADDRESS_TYPES_URL + FIRST_ADDRESS_TYPE_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.status").value(String.valueOf(HttpStatus.NOT_FOUND.value())))
                .andExpect(jsonPath("$.error").isNotEmpty())
                .andExpect(jsonPath("$.messages").isNotEmpty());
        verify(addressTypeServiceGetter).findById(FIRST_ADDRESS_TYPE_ID);
    }
}