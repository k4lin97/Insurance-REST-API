package com.kalin.insurance.insurancerestapi.client;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for the client.
 */
public interface ClientRepository extends JpaRepository<Client, Long> {
}
