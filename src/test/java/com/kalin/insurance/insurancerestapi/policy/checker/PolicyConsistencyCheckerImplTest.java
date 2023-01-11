package com.kalin.insurance.insurancerestapi.policy.checker;

import com.kalin.insurance.insurancerestapi.policy.Policy;
import com.kalin.insurance.insurancerestapi.policy.exception.PolicyNotConsistentException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PolicyConsistencyCheckerImplTest {
    private static final Long POLICY_ID = 1L;

    @Autowired
    private PolicyConsistencyCheckerImpl policyConsistencyChecker;

    private static Policy policy;
    private static Calendar calendar;
    private static List<String> expectedErrors;

    @BeforeAll
    static void setup() {
        policy = new Policy();
        policy.setId(POLICY_ID);

        expectedErrors = new ArrayList<>();
        calendar = Calendar.getInstance();
    }

    @Test
    void givenConclusionDateAfterInsuranceBeginDateShouldThrowException() {
        expectedErrors.add("Conclusion date cannot be after the insurance begin date.");
        calendar.set(2022, Calendar.DECEMBER, 17);
        policy.setConclusionDate(calendar.getTime());
        calendar.set(2022, Calendar.DECEMBER, 15);
        policy.setInsuranceBeginDate(calendar.getTime());
        calendar.set(2023, Calendar.DECEMBER, 15);
        policy.setInsuranceEndDate(calendar.getTime());

        PolicyNotConsistentException policyNotConsistentException = assertThrows(PolicyNotConsistentException.class,
                () -> policyConsistencyChecker.checkPolicyConsistency(policy));
        assertEquals(expectedErrors, policyNotConsistentException.getErrors());
    }

    @Test
    void givenConclusionDateAfterInsuranceEndDateShouldThrowException() {
        expectedErrors.add("Conclusion date cannot be after the insurance end date.");
        calendar.set(2024, Calendar.DECEMBER, 17);
        policy.setConclusionDate(calendar.getTime());
        calendar.set(2022, Calendar.DECEMBER, 15);
        policy.setInsuranceBeginDate(calendar.getTime());
        calendar.set(2023, Calendar.DECEMBER, 15);
        policy.setInsuranceEndDate(calendar.getTime());

        PolicyNotConsistentException policyNotConsistentException = assertThrows(PolicyNotConsistentException.class,
                () -> policyConsistencyChecker.checkPolicyConsistency(policy));
        assertEquals(expectedErrors, policyNotConsistentException.getErrors());
    }

    @Test
    void givenInsuranceBeginDateAfterInsuranceEndDateShouldThrowException() {
        expectedErrors.add("Insurance begin date cannot be after insurance end date.");
        calendar.set(2022, Calendar.DECEMBER, 17);
        policy.setConclusionDate(calendar.getTime());
        calendar.set(2022, Calendar.DECEMBER, 15);
        policy.setInsuranceBeginDate(calendar.getTime());
        calendar.set(2021, Calendar.DECEMBER, 15);
        policy.setInsuranceEndDate(calendar.getTime());

        PolicyNotConsistentException policyNotConsistentException = assertThrows(PolicyNotConsistentException.class,
                () -> policyConsistencyChecker.checkPolicyConsistency(policy));
        assertEquals(expectedErrors, policyNotConsistentException.getErrors());
    }

    @Test
    void givenConsistentPolicyShouldNotThrowAnything() {
        calendar.set(2022, Calendar.DECEMBER, 17);
        policy.setConclusionDate(calendar.getTime());
        calendar.set(2022, Calendar.DECEMBER, 21);
        policy.setInsuranceBeginDate(calendar.getTime());
        calendar.set(2023, Calendar.DECEMBER, 21);
        policy.setInsuranceEndDate(calendar.getTime());

        assertDoesNotThrow(() -> policyConsistencyChecker.checkPolicyConsistency(policy));
    }
}