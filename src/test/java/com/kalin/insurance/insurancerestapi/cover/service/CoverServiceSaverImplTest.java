package com.kalin.insurance.insurancerestapi.cover.service;

import com.kalin.insurance.insurancerestapi.car.Car;
import com.kalin.insurance.insurancerestapi.car.exception.CarNotFoundException;
import com.kalin.insurance.insurancerestapi.car.service.CarServiceGetter;
import com.kalin.insurance.insurancerestapi.cover.Cover;
import com.kalin.insurance.insurancerestapi.cover.CoverRepository;
import com.kalin.insurance.insurancerestapi.cover.checker.CoverToCarAssignedChecker;
import com.kalin.insurance.insurancerestapi.cover.exception.CoverDuplicatedException;
import com.kalin.insurance.insurancerestapi.model.checker.ExistenceCheckerById;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest
class CoverServiceSaverImplTest {
    private static final Long COVER_ID = 1L;

    @InjectMocks
    private CoverServiceSaverImpl coverServiceSaver;
    @Mock
    private CoverRepository coverRepository;
    @Mock
    private CarServiceGetter carServiceGetter;
    @Mock
    @Qualifier("carExistenceCheckerById")
    private ExistenceCheckerById carExistenceCheckerById;
    @Mock
    private CoverToCarAssignedChecker coverToCarAssignedChecker;

    private static Cover cover;
    private static Car car;

    @BeforeAll
    static void setup() {
        cover = new Cover();
        cover.setId(COVER_ID);

        car = new Car();
        car.setId(1L);

        cover.setCar(car);
    }

    @Test
    void givenCoverShouldReturnAndSaveCover() {
        doNothing().when(carExistenceCheckerById).checkIfAlreadyExists(cover.getCar().getId());
        doNothing().when(coverToCarAssignedChecker).checkIfCoverToCarAssigned(cover);
        given(carServiceGetter.getReferenceById(cover.getCar().getId())).willReturn(car);
        given(coverRepository.save(cover)).willReturn(cover);

        assertEquals(cover, coverServiceSaver.save(cover));
        verify(coverRepository).save(cover);
    }

    @Test
    void givenNonExistingCarShouldThrowCarNotFoundException() {
        doThrow(CarNotFoundException.class).when(carExistenceCheckerById).checkIfAlreadyExists(cover.getCar().getId());
        assertThrows(CarNotFoundException.class, () -> coverServiceSaver.save(cover));
    }

    @Test
    void givenDuplicatedCoverShouldThrowCoverDuplicatedException() {
        doNothing().when(carExistenceCheckerById).checkIfAlreadyExists(cover.getCar().getId());
        doThrow(CoverDuplicatedException.class).when(coverToCarAssignedChecker).checkIfCoverToCarAssigned(cover);
        assertThrows(CoverDuplicatedException.class, () -> coverServiceSaver.save(cover));
    }
}