package com.kalin.insurance.insurancerestapi.cover.checker;

import com.kalin.insurance.insurancerestapi.car.Car;
import com.kalin.insurance.insurancerestapi.car.service.CarServiceGetter;
import com.kalin.insurance.insurancerestapi.cover.Cover;
import com.kalin.insurance.insurancerestapi.cover.exception.CoverDuplicatedException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class CoverToCarAssignedCheckerImplTest {
    private static final Long COVER_ID = 1L;

    @Autowired
    private CoverToCarAssignedCheckerImpl coverToCarAssignedChecker;
    @MockBean
    private CarServiceGetter carServiceGetter;

    private static Cover cover;
    private static Car car;

    @BeforeAll
    static void setup() {
        cover = new Cover();
        cover.setId(COVER_ID);
        car = new Car();
    }

    @Test
    void givenUniqueCoverShouldNotDoAnything() {
        Long carId = 1L;
        car.setId(carId);
        cover.setCar(car);
        Set<Cover> covers = new HashSet<>();
        car.setCovers(covers);

        given(carServiceGetter.getReferenceById(carId)).willReturn(car);
        assertDoesNotThrow(() -> coverToCarAssignedChecker.checkIfCoverToCarAssigned(cover));
    }

    @Test
    void givenCoverAlreadyAssignedToCarShouldThrowCoverDuplicatedException() {
        Long carId = 1L;
        car.setId(carId);
        cover.setCar(car);
        Set<Cover> covers = new HashSet<>();
        covers.add(cover);
        car.setCovers(covers);

        given(carServiceGetter.getReferenceById(carId)).willReturn(car);
        assertThrows(CoverDuplicatedException.class, () -> coverToCarAssignedChecker.checkIfCoverToCarAssigned(cover));
    }
}