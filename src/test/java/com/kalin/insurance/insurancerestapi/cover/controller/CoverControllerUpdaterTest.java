package com.kalin.insurance.insurancerestapi.cover.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kalin.insurance.insurancerestapi.car.Car;
import com.kalin.insurance.insurancerestapi.cover.Cover;
import com.kalin.insurance.insurancerestapi.cover.exception.CoverNotFoundException;
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

@WebMvcTest(CoverControllerUpdater.class)
@WithMockUser
class CoverControllerUpdaterTest {
    private static final Long COVER_ID = 1L;
    private static final String COVER_URL = "/covers/";
    private static final String PUT_VALID_COVER_CONTENT = "{\"type\": \"NWW\",\"car\": {\"id\": 1}}";
    private static final String PUT_NOT_VALID_COVER_CONTENT = "{\"type\": \"\",\"car\": {\"id\": 1}}";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ServiceUpdater<Cover> coverServiceUpdater;

    private static Cover cover;

    @BeforeAll
    static void setup() {
        cover = new Cover();
        cover.setId(COVER_ID);
        Car firstCar = new Car();
        firstCar.setId(1L);
        cover.setCar(firstCar);
    }

    @Test
    void givenExistingIdShouldReturnUpdatedCover() throws Exception {
        given(coverServiceUpdater.update(any(), any())).willReturn(cover);

        mockMvc.perform(put(COVER_URL + COVER_ID)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(PUT_VALID_COVER_CONTENT))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(cover)));
    }

    @Test
    void givenNonExistingIdShouldReturnNotFound() throws Exception {
        doThrow(CoverNotFoundException.class).when(coverServiceUpdater).update(any(), any());

        mockMvc.perform(put(COVER_URL + COVER_ID)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(PUT_VALID_COVER_CONTENT))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.status").value(String.valueOf(HttpStatus.NOT_FOUND.value())))
                .andExpect(jsonPath("$.error").isNotEmpty())
                .andExpect(jsonPath("$.messages").isNotEmpty());
    }

    @Test
    void givenWrongCoverPutInputShouldReturnBadRequest() throws Exception {
        mockMvc.perform(put(COVER_URL + COVER_ID)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(PUT_NOT_VALID_COVER_CONTENT))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.status").value(String.valueOf(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.error").isNotEmpty())
                .andExpect(jsonPath("$.messages").isNotEmpty());
    }
}