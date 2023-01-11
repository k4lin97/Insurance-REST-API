package com.kalin.insurance.insurancerestapi.policy;

import com.kalin.insurance.insurancerestapi.car.Car;
import com.kalin.insurance.insurancerestapi.cover.Cover;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PolicyTest {
    private static final Long POLICY_ID = 1L;
    private static Policy policy;

    @BeforeAll
    static void setup() {
        policy = new Policy();
        policy.setId(POLICY_ID);

        Cover cover = new Cover();
        cover.setType(Cover.Type.OC);
        Car car = new Car();
        car.setInsuredValue(1000);
        List<Car> cars = new ArrayList<>();
        cover.setCar(car);
        Set<Cover> covers = new HashSet<>();
        covers.add(cover);
        car.setCovers(covers);
        cars.add(car);

        policy.setCars(cars);
        policy.setTotalPremium(100);
    }

    @Test
    void testCalculateTotalPremium() {
        assertEquals(100, policy.getTotalPremium());
        policy.calculateTotalPremium();
        assertEquals(1050, policy.getTotalPremium());
    }
}