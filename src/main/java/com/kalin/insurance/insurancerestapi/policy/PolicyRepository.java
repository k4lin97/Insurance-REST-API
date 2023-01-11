package com.kalin.insurance.insurancerestapi.policy;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for the policy.
 */
public interface PolicyRepository extends JpaRepository<Policy, Long> {
}
