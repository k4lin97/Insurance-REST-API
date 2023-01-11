package com.kalin.insurance.insurancerestapi.model.service;

/**
 * Defines methods needed to get a reference to entity, by given ID.
 *
 * @param <T> Entity type to get.
 */
public interface ServiceGetterReferenceById<T> {
    T getReferenceById(Long id);
}
