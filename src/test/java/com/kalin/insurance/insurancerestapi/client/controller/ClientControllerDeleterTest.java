package com.kalin.insurance.insurancerestapi.client.controller;

import com.kalin.insurance.insurancerestapi.client.exception.ClientNotFoundException;
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

@WebMvcTest(ClientControllerDeleter.class)
@WithMockUser
class ClientControllerDeleterTest {
    private static final Long CLIENT_ID = 1L;
    private static final String CLIENT_URL = "/clients/";

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    @Qualifier("clientServiceDeleterImpl")
    private ServiceDeleter clientServiceDeleter;

    @Test
    void givenClientShouldReturnSuccess() throws Exception {
        doNothing().when(clientServiceDeleter).deleteById(CLIENT_ID);

        mockMvc.perform(delete(CLIENT_URL + CLIENT_ID).with(csrf()))
                .andExpect(status().isOk());

        verify(clientServiceDeleter).deleteById(CLIENT_ID);
    }

    @Test
    void givenClientNotFoundShouldReturnNOtFound() throws Exception {
        doThrow(ClientNotFoundException.class).when(clientServiceDeleter).deleteById(CLIENT_ID);

        mockMvc.perform(MockMvcRequestBuilders.delete(CLIENT_URL + CLIENT_ID).with(csrf()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.status").value(String.valueOf(HttpStatus.NOT_FOUND.value())))
                .andExpect(jsonPath("$.error").isNotEmpty())
                .andExpect(jsonPath("$.messages").isNotEmpty());

        verify(clientServiceDeleter).deleteById(CLIENT_ID);
    }
}