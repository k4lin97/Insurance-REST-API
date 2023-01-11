package com.kalin.insurance.insurancerestapi.car.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kalin.insurance.insurancerestapi.car.Car;
import com.kalin.insurance.insurancerestapi.car.exception.CarNotFoundException;
import com.kalin.insurance.insurancerestapi.car.service.CarServiceGetter;
import com.kalin.insurance.insurancerestapi.policy.Policy;
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
import java.util.HashSet;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(CarControllerGetter.class)
@WithMockUser
class CarControllerGetterTest {
    private static final Long FIRST_CAR_ID = 1L;
    private static final Long SECOND_CAR_ID = 2L;
    private static final String CAR_URL = "/cars/";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private CarServiceGetter carServiceGetter;

    private static Car car;
    private static List<Car> cars;

    @BeforeAll
    static void setup() {
        car = new Car();
        car.setId(FIRST_CAR_ID);
        Policy policy = new Policy();
        policy.setId(1L);
        car.setPolicy(policy);
        car.setCovers(new HashSet<>());
        car.setMake("audi");
        car.setType("sedan");
        car.setInsuredValue(10000);
        car.setProductionYear(2016);
        car.setRegistrationNumber("GD123");

        Car secondCar = new Car();
        secondCar.setId(SECOND_CAR_ID);
        Policy newPolicy = new Policy();
        newPolicy.setId(2L);
        secondCar.setPolicy(newPolicy);
        secondCar.setCovers(new HashSet<>());
        secondCar.setMake("bmw");
        secondCar.setType("suv");
        secondCar.setInsuredValue(5000);
        secondCar.setProductionYear(2009);
        secondCar.setRegistrationNumber("GD455");

        cars = new ArrayList<>();
        cars.add(car);
        cars.add(secondCar);
    }

    @Test
    void givenCarsShouldReturnCars() throws Exception {
        when(carServiceGetter.findAll()).thenReturn(cars);

        StringWriter stringWriter = new StringWriter();
        objectMapper.writeValue(stringWriter, cars);
        final String expectedJson = stringWriter.toString();

        mockMvc.perform(get(CAR_URL))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));

        verify(carServiceGetter).findAll();
    }

    @Test
    void givenCarShouldReturnCarById() throws Exception {
        when(carServiceGetter.findById(FIRST_CAR_ID)).thenReturn(car);

        final String expectedJson = objectMapper.writeValueAsString(car);

        mockMvc.perform(get(CAR_URL + FIRST_CAR_ID))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
        verify(carServiceGetter).findById(FIRST_CAR_ID);
    }

    @Test
    void notGivenCarShouldReturnNotFound() throws Exception {
        when(carServiceGetter.findById(FIRST_CAR_ID)).thenThrow(CarNotFoundException.class);

        mockMvc.perform(get(CAR_URL + FIRST_CAR_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.status").value(String.valueOf(HttpStatus.NOT_FOUND.value())))
                .andExpect(jsonPath("$.error").isNotEmpty())
                .andExpect(jsonPath("$.messages").isNotEmpty());
        verify(carServiceGetter).findById(FIRST_CAR_ID);
    }
}