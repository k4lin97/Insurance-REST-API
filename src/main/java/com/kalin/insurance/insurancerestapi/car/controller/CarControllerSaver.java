package com.kalin.insurance.insurancerestapi.car.controller;

import com.kalin.insurance.insurancerestapi.car.Car;
import com.kalin.insurance.insurancerestapi.model.service.ServiceSaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Rest controller that handles car POST requests. Used for creating new cars.
 */
@RestController
@RequestMapping("/cars")
public class CarControllerSaver {
    private final ServiceSaver<Car> carServiceSaver;

    @Autowired
    public CarControllerSaver(ServiceSaver<Car> carServiceSaver) {
        this.carServiceSaver = carServiceSaver;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Car save(@Valid @RequestBody Car car) {
        return carServiceSaver.save(car);
    }
}
