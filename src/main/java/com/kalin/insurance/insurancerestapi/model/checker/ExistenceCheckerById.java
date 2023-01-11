package com.kalin.insurance.insurancerestapi.model.checker;

/**
 * Checks whether the entity exists in a database, by given ID.
 */
public interface ExistenceCheckerById {
    void checkIfAlreadyExists(Long id);
}
