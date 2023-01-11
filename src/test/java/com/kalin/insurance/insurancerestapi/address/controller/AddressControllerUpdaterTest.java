package com.kalin.insurance.insurancerestapi.address.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kalin.insurance.insurancerestapi.address.Address;
import com.kalin.insurance.insurancerestapi.address.exception.AddressNotFoundException;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(AddressControllerUpdater.class)
@WithMockUser
class AddressControllerUpdaterTest {
    private static final Long ADDRESS_TYPE_ID = 1L;
    private static final Long ADDRESS_ID = 1L;
    private static final String ADDRESS_URL = "/addresses/";
    private static final String PUT_VALID_ADDRESS_CONTENT = "{\"addressType\": {\"type\": \"home\"},\"country\": \"Poland\",\"city\": \"Gdansk\"," +
            "\"postCode\": \"12-593\",\"street\": \"Zielona\",\"streetNumber\": \"2\"}";
    private static final String PUT_NOT_VALID_ADDRESS_CONTENT = "{\"addressType\": {\"type\": \"home\"},\"country\": \"Poland\"," +
            "\"city\": \"Gdansk\",\"postCode\": \"12-593ss\",\"street\": \"Zielona\",\"streetNumber\": \"2\"}";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ServiceUpdater<Address> addressServiceUpdater;

    private static Address address;

    @BeforeAll
    static void setup() {
        AddressType addressType = new AddressType();
        addressType.setId(ADDRESS_TYPE_ID);
        addressType.setType("home");

        address = new Address();
        address.setId(ADDRESS_ID);
        address.setAddressType(addressType);
        address.setCity("Gdansk");
        address.setPostCode("12-593");
        address.setCountry("Poland");
        address.setStreet("Zielona");
        address.setStreetNumber("2");
    }

    @Test
    void givenExistingIdShouldReturnUpdatedAddress() throws Exception {
        given(addressServiceUpdater.update(any(), any())).willReturn(address);

        mockMvc.perform(put(ADDRESS_URL + ADDRESS_ID)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(PUT_VALID_ADDRESS_CONTENT))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(address)));
    }

    @Test
    void givenNonExistingIdShouldReturnNotFound() throws Exception {
        doThrow(AddressNotFoundException.class).when(addressServiceUpdater).update(any(), any());

        mockMvc.perform(put(ADDRESS_URL + ADDRESS_ID)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(PUT_VALID_ADDRESS_CONTENT))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.status").value(String.valueOf(HttpStatus.NOT_FOUND.value())))
                .andExpect(jsonPath("$.error").isNotEmpty())
                .andExpect(jsonPath("$.messages").isNotEmpty());
    }

    @Test
    void givenWrongAddressPutInputShouldReturnBadRequest() throws Exception {
        mockMvc.perform(put(ADDRESS_URL + ADDRESS_ID)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(PUT_NOT_VALID_ADDRESS_CONTENT))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.status").value(String.valueOf(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.error").isNotEmpty())
                .andExpect(jsonPath("$.messages").isNotEmpty());
    }
}