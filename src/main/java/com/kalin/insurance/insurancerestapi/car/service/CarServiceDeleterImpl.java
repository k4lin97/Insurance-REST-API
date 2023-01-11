package com.kalin.insurance.insurancerestapi.car.service;

import com.kalin.insurance.insurancerestapi.car.CarRepository;
import com.kalin.insurance.insurancerestapi.model.checker.ExistenceCheckerById;
import com.kalin.insurance.insurancerestapi.model.service.ServiceDeleter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Service used to deleting cars from database.
 */
@Service
@Qualifier("carServiceDeleter")
public class CarServiceDeleterImpl implements ServiceDeleter {
    private final CarRepository carRepository;
    private final ExistenceCheckerById existenceCheckerById;

    @Autowired
    public CarServiceDeleterImpl(CarRepository carRepository,
                                 @Qualifier("carExistenceCheckerById") ExistenceCheckerById existenceCheckerById) {
        this.carRepository = carRepository;
        this.existenceCheckerById = existenceCheckerById;
    }

    @Override
    public void deleteById(Long id) {
        existenceCheckerById.checkIfAlreadyExists(id);
        carRepository.deleteById(id);
    }
}
