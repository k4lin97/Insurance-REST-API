package com.kalin.insurance.insurancerestapi.car.controller;

import com.kalin.insurance.insurancerestapi.car.Car;
import com.kalin.insurance.insurancerestapi.car.service.CarServiceGetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Rest controller that handles car GET requests. Used for getting cars.
 */
@RestController
@RequestMapping("/cars")
public class CarControllerGetter {
    private final CarServiceGetter carServiceGetter;

    @Autowired
    public CarControllerGetter(CarServiceGetter carServiceGetter) {
        this.carServiceGetter = carServiceGetter;
    }

    @GetMapping()
    public List<Car> findAll() {
        return carServiceGetter.findAll();
    }

    @GetMapping("/{id}")
    public Car findById(@PathVariable("id") Long id) {
        return carServiceGetter.findById(id);
    }
}
