package com.kalin.insurance.insurancerestapi.policy.checker;

import com.kalin.insurance.insurancerestapi.policy.Policy;
import com.kalin.insurance.insurancerestapi.policy.exception.PolicyNotConsistentException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Checks if the policy is consistent.
 */
@Component
public class PolicyConsistencyCheckerImpl implements PolicyConsistencyChecker {
    /**
     * Policy is not consistent when:
     * - conclusion date is after the insurance begin date;
     * - conclusion date is after the insurance end date;
     * - insurance begin date is after insurance end date;
     *
     * @param policy policy to check.
     * @throws PolicyNotConsistentException thrown in case the policy is not consistent.
     */
    @Override
    public void checkPolicyConsistency(Policy policy) throws PolicyNotConsistentException {
        List<String> errors = new ArrayList<>();
        Date conclusionDate = policy.getConclusionDate();
        Date insBeginDate = policy.getInsuranceBeginDate();
        Date insEndDate = policy.getInsuranceEndDate();

        if (conclusionDate.compareTo(insBeginDate) > 0) {
            errors.add("Conclusion date cannot be after the insurance begin date.");
        }
        if (conclusionDate.compareTo(insEndDate) > 0) {
            errors.add("Conclusion date cannot be after the insurance end date.");
        }
        if (insBeginDate.compareTo(insEndDate) > 0) {
            errors.add("Insurance begin date cannot be after insurance end date.");
        }

        if (!errors.isEmpty()) {
            throw new PolicyNotConsistentException(errors);
        }
    }
}
