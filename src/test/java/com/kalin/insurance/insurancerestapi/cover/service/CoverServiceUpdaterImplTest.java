package com.kalin.insurance.insurancerestapi.cover.service;

import com.kalin.insurance.insurancerestapi.car.Car;
import com.kalin.insurance.insurancerestapi.cover.Cover;
import com.kalin.insurance.insurancerestapi.cover.exception.CoverNotFoundException;
import com.kalin.insurance.insurancerestapi.model.checker.ExistenceCheckerById;
import com.kalin.insurance.insurancerestapi.model.service.ServiceSaver;
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
class CoverServiceUpdaterImplTest {
    private static final Long COVER_ID = 1L;

    @InjectMocks
    private CoverServiceUpdaterImpl coverServiceUpdater;
    @Mock
    @Qualifier("coverExistenceCheckerById")
    private ExistenceCheckerById existenceCheckerById;
    @Mock
    private ServiceSaver<Cover> coverServiceSaver;

    private static Cover cover;

    @BeforeAll
    static void setup() {
        cover = new Cover();
        cover.setId(COVER_ID);
        Car firstCar = new Car();
        firstCar.setId(1L);
        cover.setCar(firstCar);
    }

    @Test
    void givenCoverIdShouldReturnAndUpdateCover() {
        doNothing().when(existenceCheckerById).checkIfAlreadyExists(COVER_ID);
        given(coverServiceSaver.save(cover)).willReturn(cover);

        assertEquals(cover, coverServiceUpdater.update(cover, COVER_ID));
        verify(coverServiceSaver).save(cover);
    }

    @Test
    void givenNotExistingCoverIdShouldThrowCoverNotFoundException() {
        doThrow(CoverNotFoundException.class).when(existenceCheckerById).checkIfAlreadyExists(COVER_ID);
        assertThrows(CoverNotFoundException.class, () -> coverServiceUpdater.update(cover, COVER_ID));
    }
}