package com.kalin.insurance.insurancerestapi.cover.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kalin.insurance.insurancerestapi.car.Car;
import com.kalin.insurance.insurancerestapi.cover.Cover;
import com.kalin.insurance.insurancerestapi.cover.exception.CoverNotFoundException;
import com.kalin.insurance.insurancerestapi.cover.service.CoverServiceGetter;
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

@WebMvcTest(CoverControllerGetter.class)
@WithMockUser
class CoverControllerGetterTest {
    private static final Long FIRST_COVER_ID = 1L;
    private static final Long SECOND_COVER_ID = 1L;
    private static final String COVER_URL = "/covers/";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private CoverServiceGetter coverServiceGetter;

    private static Cover cover;
    private static List<Cover> covers;

    @BeforeAll
    static void setup() {
        cover = new Cover();
        cover.setId(FIRST_COVER_ID);
        Car firstCar = new Car();
        firstCar.setId(1L);
        cover.setCar(firstCar);

        Cover secondCover = new Cover();
        secondCover.setId(SECOND_COVER_ID);
        Car secondCar = new Car();
        secondCar.setId(2L);
        secondCover.setCar(secondCar);

        covers = new ArrayList<>();
        covers.add(cover);
        covers.add(secondCover);
    }

    @Test
    void givenCoversShouldReturnCovers() throws Exception {
        when(coverServiceGetter.findAll()).thenReturn(covers);

        StringWriter stringWriter = new StringWriter();
        objectMapper.writeValue(stringWriter, covers);
        final String expectedJson = stringWriter.toString();

        mockMvc.perform(get(COVER_URL))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));

        verify(coverServiceGetter).findAll();
    }

    @Test
    void givenCoverShouldReturnCoverById() throws Exception {
        when(coverServiceGetter.findById(FIRST_COVER_ID)).thenReturn(cover);

        final String expectedJson = objectMapper.writeValueAsString(cover);

        mockMvc.perform(get(COVER_URL + FIRST_COVER_ID))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
        verify(coverServiceGetter).findById(FIRST_COVER_ID);
    }

    @Test
    void notGivenCoverShouldReturnNotFound() throws Exception {
        when(coverServiceGetter.findById(FIRST_COVER_ID)).thenThrow(CoverNotFoundException.class);

        mockMvc.perform(get(COVER_URL + FIRST_COVER_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.status").value(String.valueOf(HttpStatus.NOT_FOUND.value())))
                .andExpect(jsonPath("$.error").isNotEmpty())
                .andExpect(jsonPath("$.messages").isNotEmpty());
        verify(coverServiceGetter).findById(FIRST_COVER_ID);
    }
}