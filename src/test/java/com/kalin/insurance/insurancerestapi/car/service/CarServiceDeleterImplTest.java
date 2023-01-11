package com.kalin.insurance.insurancerestapi.car.service;

import com.kalin.insurance.insurancerestapi.car.CarRepository;
import com.kalin.insurance.insurancerestapi.car.exception.CarNotFoundException;
import com.kalin.insurance.insurancerestapi.model.checker.ExistenceCheckerById;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class CarServiceDeleterImplTest {
    private static final Long CAR_ID = 1L;

    @InjectMocks
    private CarServiceDeleterImpl carServiceDeleter;
    @Mock
    private CarRepository carRepository;
    @Mock
    @Qualifier("carExistenceCheckerById")
    private ExistenceCheckerById existenceCheckerById;

    @Test
    void givenCarIdShouldNotReturnAnythingAndDelete() {
        doNothing().when(existenceCheckerById).checkIfAlreadyExists(CAR_ID);
        doNothing().when(carRepository).deleteById(CAR_ID);

        assertDoesNotThrow(() -> carServiceDeleter.deleteById(CAR_ID));
        verify(carRepository).deleteById(CAR_ID);
    }

    @Test
    void notGivenCarIdShouldThrowNotFound() {
        doThrow(CarNotFoundException.class).when(existenceCheckerById).checkIfAlreadyExists(CAR_ID);
        doNothing().when(carRepository).deleteById(CAR_ID);

        assertThrows(CarNotFoundException.class, () -> carServiceDeleter.deleteById(CAR_ID));
    }
}