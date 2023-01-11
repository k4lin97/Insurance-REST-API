package com.kalin.insurance.insurancerestapi.policy.checker;

import com.kalin.insurance.insurancerestapi.model.checker.ExistenceCheckerById;
import com.kalin.insurance.insurancerestapi.policy.exception.PolicyNotFoundException;
import com.kalin.insurance.insurancerestapi.policy.Policy;
import com.kalin.insurance.insurancerestapi.policy.PolicyRepository;
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
class PolicyExistenceCheckerByIdImplTest {
    private static final Long POLICY_ID = 1L;
    private static Policy policy;

    @Autowired
    @Qualifier("policyExistenceCheckerById")
    private ExistenceCheckerById existenceCheckerById;
    @MockBean
    private PolicyRepository policyRepository;

    @BeforeAll
    static void setup() {
        policy = new Policy();
        policy.setId(POLICY_ID);
    }

    @Test
    void givenPolicyShouldNotThrowException() {
        given(policyRepository.findById(POLICY_ID)).willReturn(Optional.of(policy));

        assertDoesNotThrow(() -> existenceCheckerById.checkIfAlreadyExists(POLICY_ID));
        verify(policyRepository).findById(POLICY_ID);
    }

    @Test
    void notGivenPolicyShouldThrowPolicyNotFountException() {
        given(policyRepository.findById(POLICY_ID)).willReturn(Optional.empty());

        assertThrows(PolicyNotFoundException.class, () -> existenceCheckerById.checkIfAlreadyExists(POLICY_ID));
        verify(policyRepository).findById(POLICY_ID);
    }
}