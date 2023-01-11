package com.kalin.insurance.insurancerestapi.car.controller;

import com.kalin.insurance.insurancerestapi.car.exception.CarNotFoundException;
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

@WebMvcTest(CarControllerDeleter.class)
@WithMockUser
class CarControllerDeleterTest {
    private static final Long CAR_ID = 1L;
    private static final String CAR_URL = "/cars/";

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    @Qualifier("carServiceDeleterImpl")
    private ServiceDeleter carServiceDeleter;

    @Test
    void givenCarShouldReturnSuccess() throws Exception {
        doNothing().when(carServiceDeleter).deleteById(CAR_ID);

        mockMvc.perform(delete(CAR_URL + CAR_ID).with(csrf()))
                .andExpect(status().isOk());

        verify(carServiceDeleter).deleteById(CAR_ID);
    }

    @Test
    void givenCarNotFoundShouldReturnNotFound() throws Exception {
        doThrow(CarNotFoundException.class).when(carServiceDeleter).deleteById(CAR_ID);

        mockMvc.perform(MockMvcRequestBuilders.delete(CAR_URL + CAR_ID).with(csrf()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.status").value(String.valueOf(HttpStatus.NOT_FOUND.value())))
                .andExpect(jsonPath("$.error").isNotEmpty())
                .andExpect(jsonPath("$.messages").isNotEmpty());

        verify(carServiceDeleter).deleteById(CAR_ID);
    }
}