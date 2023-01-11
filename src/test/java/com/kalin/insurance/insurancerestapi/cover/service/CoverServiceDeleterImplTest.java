package com.kalin.insurance.insurancerestapi.cover.service;

import com.kalin.insurance.insurancerestapi.cover.CoverRepository;
import com.kalin.insurance.insurancerestapi.cover.exception.CoverNotFoundException;
import com.kalin.insurance.insurancerestapi.model.checker.ExistenceCheckerById;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doNothing;

@SpringBootTest
class CoverServiceDeleterImplTest {
    private static final Long COVER_ID = 1L;

    @InjectMocks
    private CoverServiceDeleterImpl coverServiceDeleter;
    @Mock
    private CoverRepository coverRepository;
    @Mock
    @Qualifier("coverExistenceCheckerById")
    private ExistenceCheckerById existenceCheckerById;

    @Test
    void givenCoverIdShouldNotReturnAnythingAndDelete() {
        doNothing().when(existenceCheckerById).checkIfAlreadyExists(COVER_ID);
        doNothing().when(coverRepository).deleteById(COVER_ID);

        assertDoesNotThrow(() -> coverServiceDeleter.deleteById(COVER_ID));
        verify(coverRepository).deleteById(COVER_ID);
    }

    @Test
    void notGivenCoverIdShouldThrowNotFound() {
        doThrow(CoverNotFoundException.class).when(existenceCheckerById).checkIfAlreadyExists(COVER_ID);
        doNothing().when(coverRepository).deleteById(COVER_ID);

        assertThrows(CoverNotFoundException.class, () -> coverServiceDeleter.deleteById(COVER_ID));
    }
}