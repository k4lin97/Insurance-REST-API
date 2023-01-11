package com.kalin.insurance.insurancerestapi.car.checker;

import com.kalin.insurance.insurancerestapi.car.Car;
import com.kalin.insurance.insurancerestapi.car.CarRepository;
import com.kalin.insurance.insurancerestapi.car.exception.CarNotFoundException;
import com.kalin.insurance.insurancerestapi.model.checker.ExistenceCheckerById;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Checks if the car, specified by ID, exists in a database.
 */
@Component
@Qualifier("carExistenceCheckerById")
public class CarExistenceCheckerByIdImpl implements ExistenceCheckerById {
    private final CarRepository carRepository;

    @Autowired
    public CarExistenceCheckerByIdImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public void checkIfAlreadyExists(Long id) throws CarNotFoundException {
        Optional<Car> carOptional = carRepository.findById(id);
        if (carOptional.isEmpty()) {
            throw new CarNotFoundException(id);
        }
    }
}
