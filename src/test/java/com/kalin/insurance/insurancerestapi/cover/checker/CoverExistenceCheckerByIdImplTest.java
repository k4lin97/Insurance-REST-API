package com.kalin.insurance.insurancerestapi.cover.checker;

import com.kalin.insurance.insurancerestapi.cover.Cover;
import com.kalin.insurance.insurancerestapi.cover.CoverRepository;
import com.kalin.insurance.insurancerestapi.cover.exception.CoverNotFoundException;
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
class CoverExistenceCheckerByIdImplTest {
    private static final Long COVER_ID = 1L;

    @Autowired
    @Qualifier("coverExistenceCheckerById")
    private ExistenceCheckerById existenceCheckerById;
    @MockBean
    private CoverRepository coverRepository;

    private static Cover cover;

    @BeforeAll
    static void setup() {
        cover = new Cover();
        cover.setId(COVER_ID);
    }

    @Test
    void givenCoverShouldNotThrowException() {
        given(coverRepository.findById(COVER_ID)).willReturn(Optional.of(cover));

        assertDoesNotThrow(() -> existenceCheckerById.checkIfAlreadyExists(COVER_ID));
        verify(coverRepository).findById(COVER_ID);
    }

    @Test
    void notGivenCoverShouldThrowCoverNotFountException() {
        given(coverRepository.findById(COVER_ID)).willReturn(Optional.empty());

        assertThrows(CoverNotFoundException.class, () -> existenceCheckerById.checkIfAlreadyExists(COVER_ID));
        verify(coverRepository).findById(COVER_ID);
    }
}