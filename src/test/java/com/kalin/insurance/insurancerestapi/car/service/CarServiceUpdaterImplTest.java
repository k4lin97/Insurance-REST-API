package com.kalin.insurance.insurancerestapi.car.service;

import com.kalin.insurance.insurancerestapi.car.Car;
import com.kalin.insurance.insurancerestapi.car.exception.CarNotFoundException;
import com.kalin.insurance.insurancerestapi.model.checker.ExistenceCheckerById;
import com.kalin.insurance.insurancerestapi.model.service.ServiceSaver;
import com.kalin.insurance.insurancerestapi.policy.Policy;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest
class CarServiceUpdaterImplTest {
    private static final Long CAR_ID = 1L;

    @InjectMocks
    private CarServiceUpdaterImpl carServiceUpdater;
    @Mock
    private ServiceSaver<Car> carServiceSaver;
    @Mock
    @Qualifier("carExistenceCheckerById")
    private ExistenceCheckerById existenceCheckerById;

    private static Car car;

    @BeforeAll
    static void setup() {
        car = new Car();
        car.setId(CAR_ID);
        Policy policy = new Policy();
        policy.setId(2L);
        car.setPolicy(policy);
        car.setCovers(new HashSet<>());
        car.setMake("audi");
        car.setType("sedan");
        car.setInsuredValue(10000);
        car.setProductionYear(2016);
        car.setRegistrationNumber("GD123");
    }

    @Test
    void givenCarIdShouldReturnAndUpdateUpdatedCar() {
        doNothing().when(existenceCheckerById).checkIfAlreadyExists(CAR_ID);
        given(carServiceSaver.save(car)).willReturn(car);

        assertEquals(car, carServiceUpdater.update(car, CAR_ID));
        verify(carServiceSaver).save(car);
    }

    @Test
    void givenNotExistingCarIdShouldThrowCarNotFoundException() {
        doThrow(CarNotFoundException.class).when(existenceCheckerById).checkIfAlreadyExists(CAR_ID);
        assertThrows(CarNotFoundException.class, () -> carServiceUpdater.update(car, CAR_ID));
    }
}