package com.kalin.insurance.insurancerestapi.cover;

import com.google.common.testing.EqualsTester;
import com.kalin.insurance.insurancerestapi.car.Car;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;


@SpringBootTest
class CoverTest {
    private static final Long COVER_ID = 1L;
    private static final Long SAME_COVER_ID = 1L;
    private static final Long DIFFERENT_COVER_ID = 1L;

    private static Cover cover;
    private static Cover sameCover;
    private static Cover differentCover;

    @BeforeAll
    static void setup() {
        cover = new Cover();
        cover.setId(COVER_ID);
        cover.setType(Cover.Type.OC);

        sameCover = new Cover();
        sameCover.setId(SAME_COVER_ID);
        sameCover.setType(Cover.Type.OC);

        differentCover = new Cover();
        differentCover.setId(DIFFERENT_COVER_ID);
        differentCover.setType(Cover.Type.AC);

        Car car = new Car();
        car.setInsuredValue(1000);
        cover.setCar(car);
    }

    @Test
    void testEqualsWithGuava() {
        new EqualsTester()
                .addEqualityGroup(cover, sameCover)
                .addEqualityGroup(differentCover)
                .testEquals();
    }

    @Test
    void testCalculatePremiumOC() {
        cover.setType(Cover.Type.OC);
        cover.calculatePremium();
        assertEquals(1050, cover.getPremium(), 0.01);
    }

    @Test
    void testCalculatePremiumAC() {
        cover.setType(Cover.Type.AC);
        cover.calculatePremium();
        assertEquals(950, cover.getPremium(), 0.01);
    }

    @Test
    void testCalculatePremiumNWW() {
        cover.setType(Cover.Type.NWW);
        cover.calculatePremium();
        assertEquals(1100, cover.getPremium(), 0.0);
    }

    @Test
    void testCalculatePremiumAssistance() {
        cover.setType(Cover.Type.ASSISTANCE);
        cover.calculatePremium();
        assertEquals(1200, cover.getPremium(), 0.01);
    }

    @Test
    void testUpdateDescription() {
        Cover newCover = new Cover();
        newCover.setType(Cover.Type.OC);
        String testDescription = "Some test description";
        newCover.setDescription(testDescription);

        assertEquals(testDescription, newCover.getDescription());
        newCover.updateDescription();
        assertEquals("OC insurance is a basic third-party liability insurance.", newCover.getDescription());
    }

    @Test
    void testPreUpdatePersist() {
        Cover coverObjectSpy = Mockito.spy(cover);
        coverObjectSpy.preUpdatePersist();
        verify(coverObjectSpy).calculatePremium();
        verify(coverObjectSpy).updateDescription();
    }
}