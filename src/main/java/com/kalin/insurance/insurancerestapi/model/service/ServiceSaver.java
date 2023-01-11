package com.kalin.insurance.insurancerestapi.model.service;

/**
 * Defines methods needed to save an entity.
 *
 * @param <T> Entity type to save.
 */
public interface ServiceSaver<T> {
    T save(T t);
}
