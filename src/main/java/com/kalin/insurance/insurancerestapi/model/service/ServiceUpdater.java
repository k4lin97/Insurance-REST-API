package com.kalin.insurance.insurancerestapi.model.service;

/**
 * Defines methods needed to update an entity.
 *
 * @param <T> Entity type to update.
 */
public interface ServiceUpdater<T> {
    T update(T t, Long id);
}
