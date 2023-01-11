package com.kalin.insurance.insurancerestapi.car.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kalin.insurance.insurancerestapi.car.Car;
import com.kalin.insurance.insurancerestapi.car.exception.CarNotFoundException;
import com.kalin.insurance.insurancerestapi.model.service.ServiceUpdater;
import com.kalin.insurance.insurancerestapi.policy.Policy;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(CarControllerUpdater.class)
@WithMockUser
class CarControllerUpdaterTest {
    private static final Long CAR_ID = 1L;
    private static final String CAR_URL = "/cars/";
    private static final String PUT_VALID_CAR_CONTENT = "{\n\"registrationNumber\": \"GD123\",\"type\": \"suv\",\"make\": \"audi\"," +
            "\"productionYear\": 2020,\"insuredValue\": 1000,\"covers\": [{\"type\": \"AC\"}," +
            "{\"type\": \"OC\"}],\"policy\": {\"id\": 1}\n}";
    private static final String PUT_NOT_VALID_CAR_CONTENT = "{\n\"registrationNumber\": \"GD123ss\",\"type\": \"suv\",\"make\": \"audi\"," +
            "\"productionYear\": 2020,\"insuredValue\": 1000,\"covers\": [{\"type\": \"AC\"}," +
            "{\"type\": \"OC\"}],\"policy\": {\"id\": 1}\n}";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ServiceUpdater<Car> carServiceUpdater;

    private static Car car;

    @BeforeAll
    static void setup() {
        car = new Car();
        car.setId(CAR_ID);
        Policy policy = new Policy();
        policy.setId(1L);
        car.setPolicy(policy);
        car.setCovers(new HashSet<>());
        car.setMake("audi");
        car.setType("sedan");
        car.setInsuredValue(10000);
        car.setProductionYear(2016);
        car.setRegistrationNumber("GD123");
    }

    @Test
    void givenExistingIdShouldReturnUpdatedCar() throws Exception {
        given(carServiceUpdater.update(any(), any())).willReturn(car);

        mockMvc.perform(put(CAR_URL + CAR_ID)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(PUT_VALID_CAR_CONTENT))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(car)));
    }

    @Test
    void givenNonExistingIdShouldReturnNotFound() throws Exception {
        doThrow(CarNotFoundException.class).when(carServiceUpdater).update(any(), any());

        mockMvc.perform(put(CAR_URL + CAR_ID)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(PUT_VALID_CAR_CONTENT))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.status").value(String.valueOf(HttpStatus.NOT_FOUND.value())))
                .andExpect(jsonPath("$.error").isNotEmpty())
                .andExpect(jsonPath("$.messages").isNotEmpty());
    }

    @Test
    void givenWrongCarPutInputShouldReturnBadRequest() throws Exception {
        mockMvc.perform(put(CAR_URL + CAR_ID)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(PUT_NOT_VALID_CAR_CONTENT))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.status").value(String.valueOf(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.error").isNotEmpty())
                .andExpect(jsonPath("$.messages").isNotEmpty());
    }
}