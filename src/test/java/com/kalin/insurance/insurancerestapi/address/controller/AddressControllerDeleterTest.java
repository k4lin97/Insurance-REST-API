package com.kalin.insurance.insurancerestapi.address.controller;

import com.kalin.insurance.insurancerestapi.address.exception.AddressNotFoundException;
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
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AddressControllerDeleter.class)
@WithMockUser
class AddressControllerDeleterTest {
    private static final Long ADDRESS_ID = 1L;
    private static final String ADDRESS_URL = "/addresses/";

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    @Qualifier("addressServiceDelete")
    private ServiceDeleter addressServiceDeleter;

    @Test
    void givenAddressShouldReturnSuccess() throws Exception {
        doNothing().when(addressServiceDeleter).deleteById(ADDRESS_ID);

        mockMvc.perform(delete(ADDRESS_URL + ADDRESS_ID).with(csrf()))
                .andExpect(status().isOk());
        verify(addressServiceDeleter).deleteById(ADDRESS_ID);
    }

    @Test
    void givenAddressNotFoundShouldReturnNotFound() throws Exception {
        doThrow(AddressNotFoundException.class).when(addressServiceDeleter).deleteById(ADDRESS_ID);

        mockMvc.perform(MockMvcRequestBuilders.delete(ADDRESS_URL + ADDRESS_ID).with(csrf()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.status").value(String.valueOf(HttpStatus.NOT_FOUND.value())))
                .andExpect(jsonPath("$.error").isNotEmpty())
                .andExpect(jsonPath("$.messages").isNotEmpty());
        verify(addressServiceDeleter).deleteById(ADDRESS_ID);
    }
}