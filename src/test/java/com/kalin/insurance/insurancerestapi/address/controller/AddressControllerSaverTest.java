package com.kalin.insurance.insurancerestapi.address.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kalin.insurance.insurancerestapi.address.Address;
import com.kalin.insurance.insurancerestapi.address.type.AddressType;
import com.kalin.insurance.insurancerestapi.address.type.exception.AddressTypeNotFoundException;
import com.kalin.insurance.insurancerestapi.model.service.ServiceSaver;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(AddressControllerSaver.class)
@WithMockUser
class AddressControllerSaverTest {
    private static final Long ADDRESS_TYPE_ID = 1L;
    private static final Long ADDRESS_ID = 1L;
    private static final String ADDRESS_URL = "/addresses/";
    private static final String POST_VALID_ADDRESS_CONTENT = "{\"addressType\": {\"type\": \"home\"},\"country\": \"Poland\",\"city\": \"Gdansk\",\"postCode\": \"12-593\",\"street\": \"Zielona\",\"streetNumber\": \"2\"}";
    private static final String POST_NOT_VALID_POST_CODE_ADDRESS_CONTENT = "{\"addressType\": {\"type\": \"home\"},\"country\": \"Poland\",\"city\": \"Gdansk\",\"postCode\": \"12-593ss\",\"street\": \"Zielona\",\"streetNumber\": \"2\"}";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ServiceSaver<Address> addressServiceSaver;

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
    void givenAddressShouldReturnAndSaveAddress() throws Exception {
        when(addressServiceSaver.save(any())).thenReturn(address);

        mockMvc.perform(post(ADDRESS_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(POST_VALID_ADDRESS_CONTENT))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(address)));
    }

    @Test
    void givenWrongAddressTypeShouldReturnAddressTypeNotFound() throws Exception {
        doThrow(AddressTypeNotFoundException.class).when(addressServiceSaver).save(any(Address.class));

        mockMvc.perform(post(ADDRESS_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(address)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.status").value(String.valueOf(HttpStatus.NOT_FOUND.value())))
                .andExpect(jsonPath("$.error").isNotEmpty())
                .andExpect(jsonPath("$.messages").isNotEmpty());
    }

    @Test
    void givenWrongAddressPostInputShouldReturnBadRequest() throws Exception {
        mockMvc.perform(post(ADDRESS_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(POST_NOT_VALID_POST_CODE_ADDRESS_CONTENT))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.status").value(String.valueOf(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.error").isNotEmpty())
                .andExpect(jsonPath("$.messages").isNotEmpty());
    }
}