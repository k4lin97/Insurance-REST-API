package com.kalin.insurance.insurancerestapi.policy.service;

import com.kalin.insurance.insurancerestapi.policy.exception.PolicyNotFoundException;
import com.kalin.insurance.insurancerestapi.policy.Policy;
import com.kalin.insurance.insurancerestapi.policy.PolicyRepository;
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
class PolicyServiceGetterImplTest {
    private static final Long FIRST_POLICY_ID = 1L;
    private static final Long SECOND_POLICY_ID = 2L;

    @InjectMocks
    private PolicyServiceGetterImpl policyServiceGetter;
    @Mock
    private PolicyRepository policyRepository;

    private static Policy policy;
    private static List<Policy> policies;

    @BeforeAll
    static void setup() {
        policy = new Policy();
        policy.setId(FIRST_POLICY_ID);

        Policy secondPolicy = new Policy();
        secondPolicy.setId(SECOND_POLICY_ID);

        policies = new ArrayList<>();
        policies.add(policy);
        policies.add(secondPolicy);
    }

    @Test
    void givenPoliciesShouldReturnPolicies() {
        given(policyRepository.findAll()).willReturn(policies);
        assertEquals(policies, policyServiceGetter.findAll());
        verify(policyRepository).findAll();
    }

    @Test
    void givenPolicyIdShouldReturnPolicyById() {
        given(policyRepository.findById(FIRST_POLICY_ID)).willReturn(Optional.of(policy));
        assertEquals(policy, policyServiceGetter.findById(FIRST_POLICY_ID));
        verify(policyRepository).findById(FIRST_POLICY_ID);
    }

    @Test
    void notGivenPolicyIdShouldThrowNotFound() {
        given(policyRepository.findById(FIRST_POLICY_ID)).willReturn(Optional.empty());
        assertThrows(PolicyNotFoundException.class, () -> policyServiceGetter.findById(FIRST_POLICY_ID));
        verify(policyRepository).findById(FIRST_POLICY_ID);
    }

    @Test
    void givenPolicyIdShouldReturnReferencePolicy() {
        given(policyRepository.getReferenceById(FIRST_POLICY_ID)).willReturn(policy);
        assertEquals(policy, policyServiceGetter.getReferenceById(FIRST_POLICY_ID));
        verify(policyRepository).getReferenceById(FIRST_POLICY_ID);
    }
}