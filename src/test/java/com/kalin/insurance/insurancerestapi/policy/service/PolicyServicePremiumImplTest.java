package com.kalin.insurance.insurancerestapi.policy.service;

import com.kalin.insurance.insurancerestapi.car.Car;
import com.kalin.insurance.insurancerestapi.cover.Cover;
import com.kalin.insurance.insurancerestapi.model.service.ServiceSaver;
import com.kalin.insurance.insurancerestapi.policy.Policy;
import com.kalin.insurance.insurancerestapi.policy.exception.PolicyNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@SpringBootTest
class PolicyServicePremiumImplTest {
    private static final Long POLICY_ID = 1L;

    @InjectMocks
    private PolicyServicePremiumImpl policyServicePremium;
    @Mock
    private PolicyServiceGetter policyServiceGetter;
    @Mock
    private ServiceSaver<Policy> policyServiceSaver;

    private static Policy policy;

    @BeforeAll
    static void setup() {
        policy = new Policy();
        policy.setId(POLICY_ID);

        Cover cover0 = new Cover();
        cover0.setType(Cover.Type.OC);
        Cover cover1 = new Cover();
        cover1.setType(Cover.Type.AC);
        Set<Cover> covers0 = new HashSet<>();
        covers0.add(cover0);
        covers0.add(cover1);
        Car car0 = new Car();
        car0.setInsuredValue(1000);
        car0.setCovers(covers0);
        cover0.setCar(car0);
        cover1.setCar(car0);

        Cover cover2 = new Cover();
        cover2.setType(Cover.Type.NWW);
        Cover cover3 = new Cover();
        cover3.setType(Cover.Type.ASSISTANCE);
        Set<Cover> covers1 = new HashSet<>();
        covers0.add(cover2);
        covers0.add(cover3);
        Car car1 = new Car();
        car1.setInsuredValue(1000);
        car1.setCovers(covers1);
        cover2.setCar(car1);
        cover3.setCar(car1);

        List<Car> cars = new ArrayList<>();
        cars.add(car0);
        cars.add(car1);
        policy.setCars(cars);
    }

    @Test
    void givenPolicyByIdShouldReturnPolicyWithCalculatedPremium() {
        policy.setTotalPremium(100d);
        assertEquals(100d, policy.getTotalPremium(), 0.01d);

        given(policyServiceGetter.findById(POLICY_ID)).willReturn(policy);
        given(policyServiceSaver.save(policy)).willReturn(policy);

        Policy calculatedPolicy = policyServicePremium.calculatePolicyPremium(POLICY_ID);

        assertEquals(4300d, calculatedPolicy.getTotalPremium(), 0.01d);
        verify(policyServiceSaver).save(policy);
    }

    @Test
    void notGivenPolicyByIdShouldThrowPolicyNotFoundException() {
        given(policyServiceGetter.findById(POLICY_ID)).willThrow(PolicyNotFoundException.class);
        assertThrows(PolicyNotFoundException.class, () -> policyServicePremium.calculatePolicyPremium(POLICY_ID));
    }
}