package com.kalin.insurance.insurancerestapi.car.service;

import com.kalin.insurance.insurancerestapi.car.Car;
import com.kalin.insurance.insurancerestapi.model.service.ServiceGetterAll;
import com.kalin.insurance.insurancerestapi.model.service.ServiceGetterById;
import com.kalin.insurance.insurancerestapi.model.service.ServiceGetterReferenceById;

/**
 * Defines methods needed to get car from database.
 */
public interface CarServiceGetter extends ServiceGetterAll<Car>, ServiceGetterById<Car>, ServiceGetterReferenceById<Car> {
}
