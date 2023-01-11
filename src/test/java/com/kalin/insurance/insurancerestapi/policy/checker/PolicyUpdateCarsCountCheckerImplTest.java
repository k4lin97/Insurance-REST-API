package com.kalin.insurance.insurancerestapi.policy.checker;

import com.kalin.insurance.insurancerestapi.car.Car;
import com.kalin.insurance.insurancerestapi.policy.Policy;
import com.kalin.insurance.insurancerestapi.policy.exception.PolicyCarsCountNotEqualException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class PolicyUpdateCarsCountCheckerImplTest {
    private static final Long NEW_POLICY_ID = 1L;
    private static final Long EXISTING_POLICY_ID = 1L;
    private static Policy newPolicy;
    private static Policy existingPolicy;
    private static List<Car> newCars;

    @Autowired
    private PolicyUpdateCarsCountCheckerImpl policyUpdateCarsCountChecker;

    @BeforeAll
    static void setup() {
        newPolicy = new Policy();
        newPolicy.setId(NEW_POLICY_ID);

        existingPolicy = new Policy();
        existingPolicy.setId(EXISTING_POLICY_ID);

        List<Car> existingCars = new ArrayList<>();
        Car car0 = new Car();
        Car car1 = new Car();
        Car car2 = new Car();
        existingCars.add(car0);
        existingCars.add(car1);
        existingCars.add(car2);
        newCars = new ArrayList<>();
        Car car3 = new Car();
        Car car4 = new Car();
        newCars.add(car3);
        newCars.add(car4);

        existingPolicy.setCars(existingCars);
        newPolicy.setCars(newCars);
    }

    @Test
    void givenDifferentCarsCountShouldThrowPolicyCarsCountNotEqualException() {
        assertThrows(PolicyCarsCountNotEqualException.class, () -> policyUpdateCarsCountChecker.checkIfExistingAndNewCarsCountIsEqual(existingPolicy, newPolicy));
    }

    @Test
    void givenSameCarsCountShouldNotThrowAnything() {
        Car car5 = new Car();
        newCars.add(car5);

        assertDoesNotThrow(() -> policyUpdateCarsCountChecker.checkIfExistingAndNewCarsCountIsEqual(existingPolicy, newPolicy));
    }
}