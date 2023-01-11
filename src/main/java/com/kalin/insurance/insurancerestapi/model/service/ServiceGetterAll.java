package com.kalin.insurance.insurancerestapi.model.service;

import java.util.List;

/**
 * Defines methods needed to get all entities.
 *
 * @param <T> Entity type to get.
 */
public interface ServiceGetterAll<T> {
    List<T> findAll();
}
