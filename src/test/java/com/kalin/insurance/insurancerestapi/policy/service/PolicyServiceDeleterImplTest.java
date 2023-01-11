package com.kalin.insurance.insurancerestapi.policy.service;

import com.kalin.insurance.insurancerestapi.model.checker.ExistenceCheckerById;
import com.kalin.insurance.insurancerestapi.policy.PolicyRepository;
import com.kalin.insurance.insurancerestapi.policy.exception.PolicyNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doNothing;

@SpringBootTest
class PolicyServiceDeleterImplTest {
    private static final Long POLICY_ID = 1L;

    @InjectMocks
    private PolicyServiceDeleterImpl policyServiceDeleter;
    @Mock
    private ExistenceCheckerById existenceCheckerById;
    @Mock
    private PolicyRepository policyRepository;

    @Test
    void givenPolicyIdShouldNotReturnAnythingAndDelete() {
        doNothing().when(existenceCheckerById).checkIfAlreadyExists(POLICY_ID);
        doNothing().when(policyRepository).deleteById(POLICY_ID);

        assertDoesNotThrow(() -> policyServiceDeleter.deleteById(POLICY_ID));
        verify(policyRepository).deleteById(POLICY_ID);
    }

    @Test
    void notGivenPolicyIdShouldThrowNotFound() {
        doThrow(PolicyNotFoundException.class).when(existenceCheckerById).checkIfAlreadyExists(POLICY_ID);
        doNothing().when(policyRepository).deleteById(POLICY_ID);

        assertThrows(PolicyNotFoundException.class, () -> policyServiceDeleter.deleteById(POLICY_ID));
    }
}