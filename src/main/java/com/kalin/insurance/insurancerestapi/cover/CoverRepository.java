package com.kalin.insurance.insurancerestapi.cover;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for the cover.
 */
public interface CoverRepository extends JpaRepository<Cover, Long> {
}
