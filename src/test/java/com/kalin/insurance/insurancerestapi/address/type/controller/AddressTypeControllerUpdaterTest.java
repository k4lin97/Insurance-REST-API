package com.kalin.insurance.insurancerestapi.address.type.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kalin.insurance.insurancerestapi.address.type.exception.AddressTypeDuplicatedException;
import com.kalin.insurance.insurancerestapi.address.type.exception.AddressTypeNotFoundException;
import com.kalin.insurance.insurancerestapi.address.type.AddressType;
import com.kalin.insurance.insurancerestapi.model.service.ServiceUpdater;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(AddressTypeControllerUpdater.class)
@WithMockUser
class AddressTypeControllerUpdaterTest {
    private static final Long ADDRESS_TYPE_ID = 1L;
    private static final String ADDRESS_TYPES_URL = "/addresses/types/";
    private static final String ADDRESS_TYPE_PUT_CONTENT = "{\"type\": \"work\"}";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ServiceUpdater<AddressType> addressTypeServiceUpdater;

    private static AddressType addressType;

    @BeforeAll
    static void setup() {
        addressType = new AddressType();
        addressType.setId(ADDRESS_TYPE_ID);
        addressType.setType("work");
    }

    @Test
    void givenUniqueAddressTypeShouldUpdateAddressType() throws Exception {
        given(addressTypeServiceUpdater.update(addressType, ADDRESS_TYPE_ID)).willReturn(addressType);

        mockMvc.perform(put(ADDRESS_TYPES_URL + ADDRESS_TYPE_ID)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ADDRESS_TYPE_PUT_CONTENT))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(addressType)));
        verify(addressTypeServiceUpdater).update(addressType, ADDRESS_TYPE_ID);
    }

    @Test
    void givenExistingAddressTypeShouldReturnConflict() throws Exception {
        doThrow(AddressTypeDuplicatedException.class).when(addressTypeServiceUpdater).update(addressType, ADDRESS_TYPE_ID);

        mockMvc.perform(put(ADDRESS_TYPES_URL + ADDRESS_TYPE_ID)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ADDRESS_TYPE_PUT_CONTENT))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.status").value(String.valueOf(HttpStatus.CONFLICT.value())))
                .andExpect(jsonPath("$.error").isNotEmpty())
                .andExpect(jsonPath("$.messages").isNotEmpty());
        verify(addressTypeServiceUpdater).update(addressType, ADDRESS_TYPE_ID);
    }

    @Test
    void givenNotExistingIdShouldReturnNotFound() throws Exception {
        doThrow(AddressTypeNotFoundException.class).when(addressTypeServiceUpdater).update(addressType, ADDRESS_TYPE_ID);

        mockMvc.perform(put(ADDRESS_TYPES_URL + ADDRESS_TYPE_ID)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ADDRESS_TYPE_PUT_CONTENT))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.status").value(String.valueOf(HttpStatus.NOT_FOUND.value())))
                .andExpect(jsonPath("$.error").isNotEmpty())
                .andExpect(jsonPath("$.messages").isNotEmpty());
        verify(addressTypeServiceUpdater).update(addressType, ADDRESS_TYPE_ID);
    }
}