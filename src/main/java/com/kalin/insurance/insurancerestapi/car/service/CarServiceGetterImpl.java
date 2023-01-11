package com.kalin.insurance.insurancerestapi.car.service;

import com.kalin.insurance.insurancerestapi.car.Car;
import com.kalin.insurance.insurancerestapi.car.CarRepository;
import com.kalin.insurance.insurancerestapi.car.exception.CarNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service used to getting cars from database.
 */
@Service
public class CarServiceGetterImpl implements CarServiceGetter {
    private final CarRepository carRepository;

    @Autowired
    public CarServiceGetterImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public List<Car> findAll() {
        return carRepository.findAll();
    }

    @Override
    public Car findById(Long id) {
        return getCarById(id);
    }

    @Override
    public Car getReferenceById(Long id) {
        return carRepository.getReferenceById(id);
    }

    private Car getCarById(Long id) throws CarNotFoundException {
        return carRepository.findById(id).orElseThrow(() -> new CarNotFoundException(id));
    }
}
