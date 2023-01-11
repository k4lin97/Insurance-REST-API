package com.kalin.insurance.insurancerestapi.car.checker;

import com.kalin.insurance.insurancerestapi.car.Car;
import com.kalin.insurance.insurancerestapi.car.CarRepository;
import com.kalin.insurance.insurancerestapi.car.exception.CarNotFoundException;
import com.kalin.insurance.insurancerestapi.model.checker.ExistenceCheckerById;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@SpringBootTest
class CarExistenceCheckerByIdImplTest {
    private static final Long CAR_ID = 1L;

    @Autowired
    @Qualifier("carExistenceCheckerById")
    private ExistenceCheckerById existenceCheckerById;
    @MockBean
    private CarRepository carRepository;

    private static Car car;

    @BeforeAll
    static void setup() {
        car = new Car();
        car.setId(CAR_ID);
    }

    @Test
    void givenCarShouldNotThrowException() {
        given(carRepository.findById(CAR_ID)).willReturn(Optional.of(car));

        assertDoesNotThrow(() -> existenceCheckerById.checkIfAlreadyExists(CAR_ID));
        verify(carRepository).findById(CAR_ID);
    }

    @Test
    void notGivenCarShouldThrowException() {
        given(carRepository.findById(CAR_ID)).willReturn(Optional.empty());

        assertThrows(CarNotFoundException.class, () -> existenceCheckerById.checkIfAlreadyExists(CAR_ID));
        verify(carRepository).findById(CAR_ID);
    }
}