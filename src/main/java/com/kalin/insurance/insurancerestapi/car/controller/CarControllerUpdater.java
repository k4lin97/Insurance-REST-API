package com.kalin.insurance.insurancerestapi.car.controller;

import com.kalin.insurance.insurancerestapi.car.Car;
import com.kalin.insurance.insurancerestapi.model.service.ServiceUpdater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Rest controller that handles car PUT requests. Used to update cars.
 */
@RestController
@RequestMapping("/cars")
public class CarControllerUpdater {
    private final ServiceUpdater<Car> carServiceUpdater;

    @Autowired
    public CarControllerUpdater(ServiceUpdater<Car> carServiceUpdater) {
        this.carServiceUpdater = carServiceUpdater;
    }

    @PutMapping("/{id}")
    public Car update(@Valid @RequestBody Car car, @PathVariable("id") Long id) {
        return carServiceUpdater.update(car, id);
    }
}
