package com.kalin.insurance.insurancerestapi.car.controller;

import com.kalin.insurance.insurancerestapi.model.service.ServiceDeleter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

/**
 * Rest controller that handles car DELETE requests. Used for deleting cars.
 */
@RestController
@RequestMapping("/cars")
public class CarControllerDeleter {
    private final ServiceDeleter carServiceDeleter;

    @Autowired
    public CarControllerDeleter(@Qualifier("carServiceDeleterImpl") ServiceDeleter carServiceDeleter) {
        this.carServiceDeleter = carServiceDeleter;
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        carServiceDeleter.deleteById(id);
    }
}
