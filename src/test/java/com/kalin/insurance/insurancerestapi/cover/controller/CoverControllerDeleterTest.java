package com.kalin.insurance.insurancerestapi.cover.controller;

import com.kalin.insurance.insurancerestapi.cover.exception.CoverNotFoundException;
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

@WebMvcTest(CoverControllerDeleter.class)
@WithMockUser
class CoverControllerDeleterTest {
    private static final Long COVER_ID = 1L;
    private static final String COVER_URL = "/covers/";

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    @Qualifier("coverServiceDeleterImpl")
    private ServiceDeleter coverServiceDeleter;

    @Test
    void givenCoverShouldReturnSuccess() throws Exception {
        doNothing().when(coverServiceDeleter).deleteById(COVER_ID);

        mockMvc.perform(delete(COVER_URL + COVER_ID).with(csrf()))
                .andExpect(status().isOk());

        verify(coverServiceDeleter).deleteById(COVER_ID);
    }

    @Test
    void givenCoverNotFoundShouldReturnNOtFound() throws Exception {
        doThrow(CoverNotFoundException.class).when(coverServiceDeleter).deleteById(COVER_ID);

        mockMvc.perform(MockMvcRequestBuilders.delete(COVER_URL + COVER_ID).with(csrf()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.status").value(String.valueOf(HttpStatus.NOT_FOUND.value())))
                .andExpect(jsonPath("$.error").isNotEmpty())
                .andExpect(jsonPath("$.messages").isNotEmpty());

        verify(coverServiceDeleter).deleteById(COVER_ID);
    }
}