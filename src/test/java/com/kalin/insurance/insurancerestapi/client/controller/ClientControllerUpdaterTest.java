package com.kalin.insurance.insurancerestapi.client.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kalin.insurance.insurancerestapi.client.exception.ClientNotFoundException;
import com.kalin.insurance.insurancerestapi.client.Client;
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

@WebMvcTest(ClientControllerUpdater.class)
@WithMockUser
class ClientControllerUpdaterTest {
    private static final Long CLIENT_ID = 1L;
    private static final String CLIENT_URL = "/clients/";
    private static final String PUT_VALID_CLIENT_CONTENT = "{\"firstName\": \"Pawel\",\"lastName\": \"Kalin\"," +
            "\"birthDate\": \"1999-10-16\",\"gender\": \"MALE\",\"address\": {\"addressType\": {\"type\": \"home\"}," +
            "\"country\": \"Poland\",\"city\": \"Gdansk\",\"postCode\": \"90-180\",\"street\": \"zielona\",\"streetNumber\": \"12a\"}}";
    private static final String PUT_NOT_VALID_CLIENT_CONTENT = "{\"firstName\": \"Pawel\",\"lastName\": \"Kalin\"," +
            "\"birthDate\": \"1999-10-16\",\"gender\": \"fffMALE\",\"address\": {\"addressType\": {\"type\": \"home\"}," +
            "\"country\": \"Poland\",\"city\": \"Gdansk\",\"postCode\": \"90-180\",\"street\": \"zielona\",\"streetNumber\": \"12a\"}}";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ServiceUpdater<Client> clientServiceUpdater;

    private static Client client;

    @BeforeAll
    static void setup() {
        client = new Client();
        client.setId(CLIENT_ID);
    }

    @Test
    void givenExistingIdShouldReturnUpdatedClient() throws Exception {
        given(clientServiceUpdater.update(any(), any())).willReturn(client);

        mockMvc.perform(put(CLIENT_URL + CLIENT_ID)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(PUT_VALID_CLIENT_CONTENT))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(client)));
    }

    @Test
    void givenNonExistingIdShouldReturnNotFound() throws Exception {
        doThrow(ClientNotFoundException.class).when(clientServiceUpdater).update(any(), any());

        mockMvc.perform(put(CLIENT_URL + CLIENT_ID)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(PUT_VALID_CLIENT_CONTENT))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.status").value(String.valueOf(HttpStatus.NOT_FOUND.value())))
                .andExpect(jsonPath("$.error").isNotEmpty())
                .andExpect(jsonPath("$.messages").isNotEmpty());
    }

    @Test
    void givenWrongClientPutInputShouldReturnBadRequest() throws Exception {
        mockMvc.perform(put(CLIENT_URL + CLIENT_ID)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(PUT_NOT_VALID_CLIENT_CONTENT))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.status").value(String.valueOf(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.error").isNotEmpty())
                .andExpect(jsonPath("$.messages").isNotEmpty());
    }
}