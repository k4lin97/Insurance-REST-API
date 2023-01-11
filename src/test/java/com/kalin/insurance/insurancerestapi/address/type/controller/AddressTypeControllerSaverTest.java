package com.kalin.insurance.insurancerestapi.address.type.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kalin.insurance.insurancerestapi.address.type.exception.AddressTypeDuplicatedException;
import com.kalin.insurance.insurancerestapi.address.type.AddressType;
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

@WebMvcTest(AddressTypeControllerSaver.class)
@WithMockUser
class AddressTypeControllerSaverTest {
    private static final String ADDRESS_TYPES_URL = "/addresses/types/";
    private static final String ADDRESS_TYPE_POST_CONTENT = "{\"type\": \"home\"}";
    private static final Long ADDRESS_TYPE_ID = 1L;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ServiceSaver<AddressType> addressTypeServiceSaver;

    private static AddressType addressType;

    @BeforeAll
    static void setup() {
        addressType = new AddressType();
        addressType.setId(ADDRESS_TYPE_ID);
        addressType.setType("home");
    }

    @Test
    void givenAddressTypeShouldReturnAndSaveAddressType() throws Exception {
        when(addressTypeServiceSaver.save(addressType)).thenReturn(addressType);

        mockMvc.perform(post(ADDRESS_TYPES_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ADDRESS_TYPE_POST_CONTENT))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(addressType)));
        verify(addressTypeServiceSaver).save(addressType);
    }

    @Test
    public void givenAddressTypeExistingShouldReturnConflict() throws Exception {
        doThrow(AddressTypeDuplicatedException.class).when(addressTypeServiceSaver).save(addressType);

        mockMvc.perform(post(ADDRESS_TYPES_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addressType)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.status").value(String.valueOf(HttpStatus.CONFLICT.value())))
                .andExpect(jsonPath("$.error").isNotEmpty())
                .andExpect(jsonPath("$.messages").isNotEmpty());
        verify(addressTypeServiceSaver).save(addressType);
    }
}