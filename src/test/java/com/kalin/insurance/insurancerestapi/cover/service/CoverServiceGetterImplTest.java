package com.kalin.insurance.insurancerestapi.cover.service;

import com.kalin.insurance.insurancerestapi.car.Car;
import com.kalin.insurance.insurancerestapi.cover.exception.CoverNotFoundException;
import com.kalin.insurance.insurancerestapi.cover.Cover;
import com.kalin.insurance.insurancerestapi.cover.CoverRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@SpringBootTest
class CoverServiceGetterImplTest {
    private static final Long FIRST_COVER_ID = 1L;
    private static final Long SECOND_COVER_ID = 1L;

    @InjectMocks
    private CoverServiceGetterImpl coverServiceGetter;
    @Mock
    private CoverRepository coverRepository;

    private static Cover cover;
    private static List<Cover> covers;

    @BeforeAll
    static void setup() {
        cover = new Cover();
        cover.setId(FIRST_COVER_ID);
        Car firstCar = new Car();
        firstCar.setId(1L);
        cover.setCar(firstCar);

        Cover secondCover = new Cover();
        secondCover.setId(SECOND_COVER_ID);
        Car secondCar = new Car();
        secondCar.setId(2L);
        secondCover.setCar(secondCar);

        covers = new ArrayList<>();
        covers.add(cover);
        covers.add(secondCover);
    }

    @Test
    void givenCoversShouldReturnCovers() {
        given(coverRepository.findAll()).willReturn(covers);
        assertEquals(covers, coverServiceGetter.findAll());
        verify(coverRepository).findAll();
    }

    @Test
    void givenCoverIdShouldReturnCoverById() {
        given(coverRepository.findById(FIRST_COVER_ID)).willReturn(Optional.of(cover));
        assertEquals(cover, coverServiceGetter.findById(FIRST_COVER_ID));
        verify(coverRepository).findById(FIRST_COVER_ID);
    }

    @Test
    void notGivenCoverIdShouldThrowNotFound() {
        given(coverRepository.findById(FIRST_COVER_ID)).willReturn(Optional.empty());
        assertThrows(CoverNotFoundException.class, () -> coverServiceGetter.findById(FIRST_COVER_ID));
        verify(coverRepository).findById(FIRST_COVER_ID);
    }
}