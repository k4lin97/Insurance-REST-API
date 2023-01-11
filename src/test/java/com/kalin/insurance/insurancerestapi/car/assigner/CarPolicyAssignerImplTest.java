package com.kalin.insurance.insurancerestapi.car.assigner;

import com.kalin.insurance.insurancerestapi.car.Car;
import com.kalin.insurance.insurancerestapi.policy.Policy;
import com.kalin.insurance.insurancerestapi.policy.service.PolicyServiceGetter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class CarPolicyAssignerImplTest {
    @Autowired
    private CarPolicyAssignerImpl carPolicyAssigner;
    @MockBean
    private PolicyServiceGetter policyServiceGetter;

    private static Policy policy;
    private static Car car;

    @BeforeAll
    static void setup() {
        policy = new Policy();
        policy.setId(1L);

        Policy carPolicy = new Policy();
        carPolicy.setId(2L);
        car = new Car();
        car.setPolicy(carPolicy);
    }

    @Test
    void shouldAssignPolicyToCar() {
        given(policyServiceGetter.getReferenceById(car.getPolicy().getId())).willReturn(policy);
        carPolicyAssigner.assignPolicyToCar(car);
        assertEquals(policy, car.getPolicy());
    }
}