package com.kalin.insurance.insurancerestapi.client.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kalin.insurance.insurancerestapi.client.exception.ClientNotFoundException;
import com.kalin.insurance.insurancerestapi.client.Client;
import com.kalin.insurance.insurancerestapi.client.service.ClientServiceGetter;
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

@WebMvcTest(ClientControllerGetter.class)
@WithMockUser
class ClientControllerGetterTest {
    private static final Long FIRST_CLIENT_ID = 1L;
    private static final Long SECOND_CLIENT_ID = 2L;
    private static final String CLIENT_URL = "/clients/";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ClientServiceGetter clientServiceGetter;

    private static Client client;
    private static List<Client> clients;

    @BeforeAll
    static void setup() {
        client = new Client();
        client.setId(FIRST_CLIENT_ID);

        Client secondClient = new Client();
        secondClient.setId(SECOND_CLIENT_ID);

        clients = new ArrayList<>();
        clients.add(client);
        clients.add(secondClient);
    }

    @Test
    void givenClientsShouldReturnClients() throws Exception {
        when(clientServiceGetter.findAll()).thenReturn(clients);

        StringWriter stringWriter = new StringWriter();
        objectMapper.writeValue(stringWriter, clients);
        final String expectedJson = stringWriter.toString();

        mockMvc.perform(get(CLIENT_URL))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));

        verify(clientServiceGetter).findAll();
    }

    @Test
    void givenClientShouldReturnClientById() throws Exception {
        when(clientServiceGetter.findById(FIRST_CLIENT_ID)).thenReturn(client);

        final String expectedJson = objectMapper.writeValueAsString(client);

        mockMvc.perform(get(CLIENT_URL + FIRST_CLIENT_ID))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
        verify(clientServiceGetter).findById(FIRST_CLIENT_ID);
    }

    @Test
    void notGivenClientShouldReturnNotFound() throws Exception {
        when(clientServiceGetter.findById(FIRST_CLIENT_ID)).thenThrow(ClientNotFoundException.class);

        mockMvc.perform(get(CLIENT_URL + FIRST_CLIENT_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.status").value(String.valueOf(HttpStatus.NOT_FOUND.value())))
                .andExpect(jsonPath("$.error").isNotEmpty())
                .andExpect(jsonPath("$.messages").isNotEmpty());
        verify(clientServiceGetter).findById(FIRST_CLIENT_ID);
    }
}