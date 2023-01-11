package com.kalin.insurance.insurancerestapi.policy.service;

import com.kalin.insurance.insurancerestapi.address.Address;
import com.kalin.insurance.insurancerestapi.address.type.AddressType;
import com.kalin.insurance.insurancerestapi.car.Car;
import com.kalin.insurance.insurancerestapi.car.assigner.CarPolicyAssigner;
import com.kalin.insurance.insurancerestapi.client.Client;
import com.kalin.insurance.insurancerestapi.model.service.ServiceSaver;
import com.kalin.insurance.insurancerestapi.policy.Policy;
import com.kalin.insurance.insurancerestapi.policy.PolicyRepository;
import com.kalin.insurance.insurancerestapi.policy.checker.PolicyConsistencyChecker;
import com.kalin.insurance.insurancerestapi.policy.checker.PolicyUpdateCarsCountChecker;
import com.kalin.insurance.insurancerestapi.policy.exception.PolicyCarsCountNotEqualException;
import com.kalin.insurance.insurancerestapi.policy.exception.PolicyNotConsistentException;
import com.kalin.insurance.insurancerestapi.policy.exception.PolicyNotFoundException;
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
import static org.mockito.Mockito.*;

@SpringBootTest
class PolicyServiceUpdaterImplTest {
    private static final Long POLICY_ID = 1L;

    @InjectMocks
    private PolicyServiceUpdaterImpl policyServiceUpdater;
    @Mock
    private PolicyRepository policyRepository;
    @Mock
    private ServiceSaver<Policy> policyServiceSaver;
    @Mock
    private PolicyConsistencyChecker policyConsistencyChecker;
    @Mock
    private PolicyUpdateCarsCountChecker policyUpdateCarsCountChecker;
    @Mock
    private CarPolicyAssigner carPolicyAssigner;

    private static Policy policy;

    @BeforeAll
    static void setup() {
        policy = new Policy();
        policy.setId(POLICY_ID);

        AddressType addressType = new AddressType();
        addressType.setType("home");

        Address address = new Address();
        address.setAddressType(addressType);

        Client client = new Client();
        client.setAddress(address);

        policy.setClient(client);

        Car car = new Car();
        List<Car> cars = new ArrayList<>();
        cars.add(car);

        policy.setCars(cars);
    }

    @Test
    void givenPolicyIdShouldReturnAndUpdatePolicy() {
        doNothing().when(policyConsistencyChecker).checkPolicyConsistency(policy);
        given(policyRepository.findById(POLICY_ID)).willReturn(Optional.of(policy));
        Policy existingPolicy = policyRepository.findById(POLICY_ID).get();
        doNothing().when(policyUpdateCarsCountChecker).checkIfExistingAndNewCarsCountIsEqual(existingPolicy, policy);
        for (Car car : policy.getCars()) {
            doNothing().when(carPolicyAssigner).assignPolicyToCar(car);
        }
        given(policyServiceSaver.save(policy)).willReturn(policy);

        assertEquals(policy, policyServiceUpdater.update(policy, POLICY_ID));
        verify(policyServiceSaver).save(policy);
    }

    @Test
    void givenNotExistingPolicyIdShouldThrowPolicyNotFoundException() {
        doNothing().when(policyConsistencyChecker).checkPolicyConsistency(policy);
        given(policyRepository.findById(POLICY_ID)).willReturn(Optional.empty());
        assertThrows(PolicyNotFoundException.class, () -> policyServiceUpdater.update(policy, POLICY_ID));
    }

    @Test
    void givenNotConsistentPolicyShouldThrowPolicyNotConsistentException() {
        doThrow(PolicyNotConsistentException.class).when(policyConsistencyChecker).checkPolicyConsistency(policy);
        assertThrows(PolicyNotConsistentException.class, () -> policyServiceUpdater.update(policy, POLICY_ID));
    }

    @Test
    void givenDifferentCarsCountShouldThrowPolicyCarsCountNotEqualException() {
        doNothing().when(policyConsistencyChecker).checkPolicyConsistency(policy);
        given(policyRepository.findById(POLICY_ID)).willReturn(Optional.of(policy));
        Policy existingPolicy = policyRepository.findById(POLICY_ID).get();
        doThrow(PolicyCarsCountNotEqualException.class).when(policyUpdateCarsCountChecker).checkIfExistingAndNewCarsCountIsEqual(existingPolicy, policy);
        assertThrows(PolicyCarsCountNotEqualException.class, () -> policyServiceUpdater.update(policy, POLICY_ID));
    }
}