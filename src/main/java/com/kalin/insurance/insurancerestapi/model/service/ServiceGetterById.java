package com.kalin.insurance.insurancerestapi.model.service;

/**
 * Defines methods needed to get an entity, by given ID.
 *
 * @param <T> Entity type to get.
 */
public interface ServiceGetterById<T> {
    T findById(Long id);
}
