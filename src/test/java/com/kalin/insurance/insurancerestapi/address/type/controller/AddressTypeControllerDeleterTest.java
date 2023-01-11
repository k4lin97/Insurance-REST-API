package com.kalin.insurance.insurancerestapi.address.type.controller;

import com.kalin.insurance.insurancerestapi.address.type.exception.AddressTypeNotFoundException;
import com.kalin.insurance.insurancerestapi.address.type.exception.AddressTypeToAddressAssignedException;
import com.kalin.insurance.insurancerestapi.model.service.ServiceDeleter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AddressTypeControllerDeleter.class)
@WithMockUser
class AddressTypeControllerDeleterTest {
    private static final Long ADDRESS_TYPE_ID = 1L;
    private static final String ADDRESS_TYPES_URL = "/addresses/types/";

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    @Qualifier("addressTypeServiceDelete")
    private ServiceDeleter addressTypeServiceDeleter;

    @Test
    void givenAddressTypeShouldReturnSuccess() throws Exception {
        doNothing().when(addressTypeServiceDeleter).deleteById(ADDRESS_TYPE_ID);
        mockMvc.perform(delete(ADDRESS_TYPES_URL + ADDRESS_TYPE_ID).with(csrf()))
                .andExpect(status().isOk());
        verify(addressTypeServiceDeleter).deleteById(ADDRESS_TYPE_ID);
    }

    @Test
    void givenAddressTypeNotFoundShouldReturnNotFound() throws Exception {
        doThrow(AddressTypeNotFoundException.class).when(addressTypeServiceDeleter).deleteById(ADDRESS_TYPE_ID);
        mockMvc.perform(MockMvcRequestBuilders.delete(ADDRESS_TYPES_URL + ADDRESS_TYPE_ID).with(csrf()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.status").value(String.valueOf(HttpStatus.NOT_FOUND.value())))
                .andExpect(jsonPath("$.error").isNotEmpty())
                .andExpect(jsonPath("$.messages").isNotEmpty());
        verify(addressTypeServiceDeleter).deleteById(ADDRESS_TYPE_ID);
    }

    @Test
    void givenAddressTypeAssignedToAddressShouldReturnConflict() throws Exception {
        doThrow(AddressTypeToAddressAssignedException.class).when(addressTypeServiceDeleter).deleteById(ADDRESS_TYPE_ID);
        mockMvc.perform(MockMvcRequestBuilders.delete(ADDRESS_TYPES_URL + ADDRESS_TYPE_ID).with(csrf()))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.status").value(String.valueOf(HttpStatus.CONFLICT.value())))
                .andExpect(jsonPath("$.error").isNotEmpty())
                .andExpect(jsonPath("$.messages").isNotEmpty());
        verify(addressTypeServiceDeleter).deleteById(ADDRESS_TYPE_ID);
    }
}