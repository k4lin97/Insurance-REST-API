package com.kalin.insurance.insurancerestapi.car.service;

import com.kalin.insurance.insurancerestapi.car.Car;
import com.kalin.insurance.insurancerestapi.car.CarRepository;
import com.kalin.insurance.insurancerestapi.car.assigner.CarPolicyAssigner;
import com.kalin.insurance.insurancerestapi.policy.Policy;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@SpringBootTest
class CarServiceSaverTest {
    private static final Long CAR_ID = 1L;

    @InjectMocks
    private CarServiceSaverImpl carServiceSaver;
    @Mock
    private CarRepository carRepository;
    @Mock
    private CarPolicyAssigner carPolicyAssigner;

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
    void givenCarShouldReturnAndSAveCar() {
        doNothing().when(carPolicyAssigner).assignPolicyToCar(car);
        given(carRepository.save(car)).willReturn(car);

        assertEquals(car, carServiceSaver.save(car));
        verify(carRepository).save(car);
    }
}