package com.kalin.insurance.insurancerestapi.car.assigner;

import com.kalin.insurance.insurancerestapi.car.Car;

/**
 * Defines methods needed to assign policy to car.
 */
public interface CarPolicyAssigner {
    void assignPolicyToCar(Car car);
}
