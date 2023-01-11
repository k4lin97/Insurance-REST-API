package com.kalin.insurance.insurancerestapi.car.service;

import com.kalin.insurance.insurancerestapi.car.Car;
import com.kalin.insurance.insurancerestapi.car.CarRepository;
import com.kalin.insurance.insurancerestapi.car.exception.CarNotFoundException;
import com.kalin.insurance.insurancerestapi.policy.Policy;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@SpringBootTest
class CarServiceGetterImplTest {
    private static final Long FIRST_CAR_ID = 1L;
    private static final Long SECOND_CAR_ID = 2L;

    @InjectMocks
    private CarServiceGetterImpl carServiceGetter;
    @Mock
    private CarRepository carRepository;

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
    void givenCarsShouldReturnCars() {
        given(carRepository.findAll()).willReturn(cars);
        assertEquals(cars, carServiceGetter.findAll());
        verify(carRepository).findAll();
    }

    @Test
    void givenCarIdShouldReturnCarById() {
        given(carRepository.findById(FIRST_CAR_ID)).willReturn(Optional.of(car));
        assertEquals(car, carServiceGetter.findById(FIRST_CAR_ID));
        verify(carRepository).findById(FIRST_CAR_ID);
    }

    @Test
    void notGivenCarIdShouldThrowNotFound() {
        given(carRepository.findById(FIRST_CAR_ID)).willReturn(Optional.empty());
        assertThrows(CarNotFoundException.class, () -> carServiceGetter.findById(FIRST_CAR_ID));
        verify(carRepository).findById(FIRST_CAR_ID);
    }

    @Test
    void givenCarIdShouldReturnCarReference() {
        given(carRepository.getReferenceById(FIRST_CAR_ID)).willReturn(car);
        assertEquals(car, carServiceGetter.getReferenceById(FIRST_CAR_ID));
        verify(carRepository).getReferenceById(FIRST_CAR_ID);
    }
}